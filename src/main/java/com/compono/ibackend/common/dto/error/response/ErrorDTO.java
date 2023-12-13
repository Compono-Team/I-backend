package com.compono.ibackend.common.dto.error.response;

import com.compono.ibackend.common.enumType.ErrorCode;
import com.compono.ibackend.common.exception.CustomException;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorDTO {

    private int code;
    private String msg;
    private String detail;

    public static ResponseEntity<ErrorDTO> toResponseEntity(CustomException ex) {
        ErrorCode errorType = ex.getErrorCode();
        String detail = ex.getDetail();

        return ResponseEntity
                .status(ex.getStatus())
                .body(ErrorDTO.builder()
                        .code(errorType.getCode())
                        .msg(errorType.getMsg())
                        .detail(detail)
                        .build());
    }
}

