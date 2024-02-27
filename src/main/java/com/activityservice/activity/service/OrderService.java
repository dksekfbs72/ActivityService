package com.activityservice.activity.service;

import com.activityservice.activity.domain.dto.OrderStatusDto;
import com.activityservice.activity.domain.entity.Order;
import com.activityservice.activity.domain.type.OrderStatus;
import com.activityservice.activity.repository.OrderRepository;
import com.activityservice.global.config.feign.StockClient;
import com.activityservice.global.exception.ActivityException;
import com.activityservice.global.exception.PaymentException;
import com.activityservice.global.type.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final StockClient stockClient;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final JdbcTemplate jdbcTemplate;

    public OrderStatusDto order(Long productId) {
        // 재고 관리 서비스 호출
        if (stockClient.order(productId).equals("재고 부족")){
            throw new ActivityException(ErrorCode.NOT_ENOUGH_STOCK);
        }
        kafkaTemplate.send("order-topic", productId.toString());
        return OrderStatusDto.builder()
                .productId(productId)
                .status(OrderStatus.PAYMENT_PAGE)
                .build();
    }

//    @KafkaListener(topics = "order-topic", groupId = "order-service")
//    public void handleOrderEvent(String productId) {
//        orderRepository.save(Order.builder()
//                        .status(OrderStatus.PAYMENT_PAGE)
//                        .relProduct(Long.parseLong(productId))
//                .build());
//    }

    @Transactional
    public void saveOrder(List<Long> productIdList) {
        String sql = "INSERT INTO order_table (rel_product, status) VALUES (?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, productIdList.get(i));
                ps.setObject(2, OrderStatus.PAYMENT_PAGE.ordinal());
            }

            @Override
            public int getBatchSize() {
                return productIdList.size();
            }
        });
    }

    @Transactional
    public OrderStatusDto paymentTry(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) throw new ActivityException(ErrorCode.NOT_FOUND_ORDER);
        Order order = optionalOrder.get();

        if (order.getStatus() == OrderStatus.ORDER_FAIL || order.getStatus() == OrderStatus.PAYMENT_SUCCESS) {
            throw new ActivityException(ErrorCode.ALREADY_PROCESSED);
        }

        if (getRandomNumber() == 0) {
            stockClient.orderFail(order.getRelProduct());
            order.setStatus(OrderStatus.ORDER_FAIL);
            throw new PaymentException(ErrorCode.CHANGE_OF_MIND, order);
        }

        order.setStatus(OrderStatus.PAYMENT_TRY);

        if (getRandomNumber() == 0) {
            stockClient.orderFail(order.getRelProduct());
            order.setStatus(OrderStatus.ORDER_FAIL);
            throw new PaymentException(ErrorCode.PAYMENT_FAIL, order);
        }

        order.setStatus(OrderStatus.PAYMENT_SUCCESS);

        return OrderStatusDto.ToDto(orderRepository.save(order));
    }

    @Transactional
    public OrderStatusDto getOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) throw new ActivityException(ErrorCode.NOT_FOUND_ORDER);
        return OrderStatusDto.ToDto(optionalOrder.get());
    }

    private int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(5);
    }
}
