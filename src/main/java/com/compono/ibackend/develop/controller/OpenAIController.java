package com.compono.ibackend.develop.controller;

import com.compono.ibackend.develop.service.openAI.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/develop/")
public class OpenAIController {
    private final OpenAIService openAIService;

    @GetMapping("v1/event")
    public ResponseEntity<Object> getEvent() {
        Object response = openAIService.getChatResponse("올해 정보처리기능사 시험을 일정을 알려줘");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
