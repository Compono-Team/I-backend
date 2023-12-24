package com.compono.ibackend.develop.controller;

import com.compono.ibackend.common.enumType.ErrorCode;
import com.compono.ibackend.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/develop/v1")
public class DevelopController {

    @GetMapping("/bad-request")
    public ResponseEntity<Object> test400Error() {
        try {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_PASSWORD);
        } catch (Exception ex) {
            throw new CustomException(ex);
        }
    }

    @GetMapping("/unauthorized")
    public ResponseEntity<Object> test401Error() {
        try {
            throw new CustomException(HttpStatus.UNAUTHORIZED, ErrorCode.EXPIRED_TOKEN);
        } catch (Exception ex) {
            throw new CustomException(ex);
        }
    }
}
