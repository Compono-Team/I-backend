package com.compono.ibackend.user.controller;

import com.compono.ibackend.user.dto.request.UserAddRequest;
import com.compono.ibackend.user.dto.response.UserAddResponse;
import com.compono.ibackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserAddResponse> addUser(@RequestBody UserAddRequest request) {
        UserAddResponse response = userService.addUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("resend-verify")
    public ResponseEntity<?> resendVerifyEmail(@RequestParam("email") String email) {
        userService.resendVerifyEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping("verify-email")
    public ResponseEntity<?> verifyEmail(
        @RequestParam("email") String email, @RequestParam("code") String code) {
        userService.verifyEmail(email, code);
        return ResponseEntity.ok().build();
    }
}
