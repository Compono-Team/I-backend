package com.compono.ibackend.common.exception;

import com.compono.ibackend.common.enumType.ErrorCode;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
    private final int code;
    private final String message;

    public BadRequestException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMsg();
    }
}
