package com.activityservice.global.exception;

import com.activityservice.activity.repository.OrderRepository;
import com.activityservice.global.dto.WebResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final OrderRepository orderRepository;

    @ExceptionHandler(ActivityException.class)
    public WebResponseData<Object> userExceptionHandler(ActivityException activityException){
        return WebResponseData.error(activityException.getErrorCode());
    }

    @ExceptionHandler(PaymentException.class)
    public WebResponseData<Object> paymentExceptionHandler(PaymentException paymentException){
        orderRepository.save(paymentException.getOrder());
        return WebResponseData.error(paymentException.getErrorCode());
    }
}
