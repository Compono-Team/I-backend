package com.compono.ibackend.common.handler;

import com.compono.ibackend.common.dto.error.response.ErrorDTO;
import com.compono.ibackend.common.enumType.ErrorCode;
import com.compono.ibackend.common.exception.BadRequestException;
import com.compono.ibackend.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorDTO> handleCustom400Exception(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        log.warn(
                String.format(
                        "http-status={%d} code={%d} msg={%s}",
                        ex.getStatus().value(), errorCode.getCode(), errorCode.getMsg()));

        return ErrorDTO.toResponseEntity(ex);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDTO> handleBadRequestException(BadRequestException ex) {
        log.warn(
                String.format(
                        "http-status={%d} code={%d} msg={%s}",
                        HttpStatus.BAD_REQUEST, ex.getCode(), ex.getMessage()));

        return ResponseEntity.badRequest().body(new ErrorDTO(ex.getCode(), ex.getMessage()));
    }
}
