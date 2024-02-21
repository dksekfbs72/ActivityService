package com.activityservice.global.exception;

import com.activityservice.activity.domain.entity.Order;
import com.activityservice.global.type.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentException extends RuntimeException{
    private ErrorCode errorCode;
    private String description;
    private Order order;

    public PaymentException(ErrorCode errorCode, Order order) {
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
        this.order = order;
    }
}
