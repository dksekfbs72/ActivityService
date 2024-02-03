package com.activityservice.global.exception;

import com.activityservice.global.dto.WebResponseData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ActivityException.class)
    public WebResponseData<Object> userExceptionHandler(ActivityException activityException){
        return WebResponseData.error(activityException.getErrorCode());
    }
}
