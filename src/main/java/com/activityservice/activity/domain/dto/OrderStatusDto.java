package com.activityservice.activity.domain.dto;

import com.activityservice.activity.domain.entity.Order;
import com.activityservice.activity.domain.type.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderStatusDto {
    private Long orderId;
    private Long productId;
    private OrderStatus status;

    public static OrderStatusDto ToDto(Order order) {
        return OrderStatusDto.builder()
                .orderId(order.getId())
                .productId(order.getRelProduct())
                .status(order.getStatus())
                .build();
    }
}
