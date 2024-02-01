package com.activityservice.global.exception;

import com.activityservice.global.type.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserException extends RuntimeException{
    private ErrorCode errorCode;
    private String description;

    public UserException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }
}
