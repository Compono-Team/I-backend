package com.compono.ibackend.common.exception;

import com.compono.ibackend.common.enumType.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus status;
    private final ErrorCode errorCode;
    private final String detail;

    public CustomException(HttpStatus status, ErrorCode errorCode) {
        this.status = status;
        this.errorCode = errorCode;
        this.detail = "";
    }

    public CustomException(HttpStatus status, ErrorCode errorCode, String detail) {
        this.status = status;
        this.errorCode = errorCode;
        this.detail = detail;
    }

    public CustomException(HttpStatus status, ErrorCode errorCode, Throwable cause) {
        this.status = status;
        this.errorCode = errorCode;
        this.detail = cause.getMessage();
    }

    public CustomException(HttpStatus status, CustomException customException) {
        this.status = status;
        this.errorCode = customException.getErrorCode();
        this.detail = customException.getDetail();
    }

    public CustomException(HttpStatus status, Throwable cause) {
        this.status = status;
        this.errorCode = ErrorCode.UNKNOWN;
        this.detail = cause.getMessage();
    }

    public CustomException(Exception exception) {
        if (exception.getClass() == CustomException.class) {
            CustomException customException = (CustomException) exception;
            this.status = customException.getStatus();
            this.errorCode = customException.getErrorCode();
            this.detail = customException.getMessage();
        } else {
            this.status = HttpStatus.BAD_REQUEST;
            this.errorCode = ErrorCode.UNKNOWN;
            this.detail = exception.getMessage();
        }
    }
}
