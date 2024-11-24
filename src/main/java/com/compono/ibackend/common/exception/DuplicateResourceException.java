package com.compono.ibackend.common.exception;

import com.compono.ibackend.common.enumType.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicateResourceException extends RuntimeException {

    private final int code;
    private final String message;

    public DuplicateResourceException(ErrorCode errorCode, String param) {
        this.code = errorCode.getCode();
        this.message = String.format(errorCode.getMsg(), param);
    }
}
