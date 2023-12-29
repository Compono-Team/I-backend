package com.compono.ibackend.user.controller;

import com.compono.ibackend.user.dto.UserAddRequestDTO;
import com.compono.ibackend.user.dto.UserAddResponseDTO;
import com.compono.ibackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserAddResponseDTO> addUser(@RequestBody UserAddRequestDTO dto) {
        UserAddResponseDTO res = userService.addUser(dto);
        return ResponseEntity.ok().body(res);
    }
}
