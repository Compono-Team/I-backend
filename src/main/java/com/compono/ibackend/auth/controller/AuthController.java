package com.compono.ibackend.auth.controller;

import com.compono.ibackend.auth.dto.response.AuthRefreshResponse;
import com.compono.ibackend.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<AuthRefreshResponse> refresh(HttpServletRequest httpServletRequest,
                                                       HttpServletResponse httpServletResponse) {

        AuthRefreshResponse res = authService.refresh(httpServletRequest, httpServletResponse);
        return ResponseEntity.ok().body(res);
    }
}
