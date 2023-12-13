package com.compono.ibackend.common.dto.error.response;

import com.compono.ibackend.common.enumType.ErrorCode;
import com.compono.ibackend.common.exception.CustomException;
import lombok.Builder;
import org.springframework.http.ResponseEntity;

@Builder
public record ErrorDTO(int code, String msg) {

    public static ResponseEntity<ErrorDTO> toResponseEntity(CustomException ex) {
        ErrorCode errorType = ex.getErrorCode();

        return ResponseEntity
                .status(ex.getStatus())
                .body(ErrorDTO.builder()
                        .code(errorType.getCode())
                        .msg(errorType.getMsg())
                        .build());
    }
}