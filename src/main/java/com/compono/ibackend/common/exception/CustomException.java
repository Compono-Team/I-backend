package com.compono.ibackend.common.exception;

import com.compono.ibackend.common.enumType.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final HttpStatus status;
    private final ErrorCode errorCode;

    public CustomException(HttpStatus status, ErrorCode errorCode) {
        this.status = status;
        this.errorCode = errorCode;
    }

    public CustomException(Exception exception) {
        if (exception.getClass() == CustomException.class) {
            CustomException customException = (CustomException) exception;
            this.status = customException.getStatus();
            this.errorCode = customException.getErrorCode();
        } else {
            this.status = HttpStatus.BAD_REQUEST;
            this.errorCode = ErrorCode.UNKNOWN;
        }
    }
}
