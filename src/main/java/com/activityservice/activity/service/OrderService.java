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
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final StockClient stockClient;

    @Transactional
    public OrderStatusDto order(Long productId) {
        // 재고 관리 서비스 호출
        if (stockClient.order(productId).equals("재고 부족")){
            throw new ActivityException(ErrorCode.NOT_ENOUGH_STOCK);
        }
        return OrderStatusDto.ToDto(orderRepository.save(Order.builder()
                .relProduct(productId)
                .status(OrderStatus.PAYMENT_PAGE)
                .build()));
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
