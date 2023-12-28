package com.compono.ibackend.develop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/develop/v1")
public class HealthCheckController {

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }
}
