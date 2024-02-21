package com.activityservice.activity.service;

import com.activityservice.activity.domain.dto.OrderStatusDto;
import com.activityservice.activity.domain.entity.Order;
import com.activityservice.activity.domain.type.OrderStatus;
import com.activityservice.activity.repository.OrderRepository;
import com.activityservice.global.config.feign.StockClient;
import com.activityservice.global.exception.ActivityException;
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
        if (stockClient.getStock(productId) <= 0) {
            throw new ActivityException(ErrorCode.NOT_ENOUGH_STOCK);
        }
        stockClient.order(productId);
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
            throw new ActivityException(ErrorCode.CHANGE_OF_MIND);
        }

        order.setStatus(OrderStatus.PAYMENT_TRY);

        if (getRandomNumber() == 0) {
            stockClient.orderFail(order.getRelProduct());
            throw new ActivityException(ErrorCode.PAYMENT_FAIL);
        }

        order.setStatus(OrderStatus.PAYMENT_SUCCESS);

        return OrderStatusDto.ToDto(orderRepository.save(order));
    }

    private int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(5);
    }
}
