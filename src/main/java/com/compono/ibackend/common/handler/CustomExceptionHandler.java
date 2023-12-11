package com.compono.ibackend.common.handler;

import com.compono.ibackend.common.dto.ErrorDTO;
import com.compono.ibackend.common.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorDTO> handleCustom400Exception(CustomException ex) {
        return ErrorDTO.toResponseEntity(ex);
    }
}
