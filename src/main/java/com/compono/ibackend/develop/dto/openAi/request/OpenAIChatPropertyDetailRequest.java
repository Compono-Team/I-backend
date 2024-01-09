package com.compono.ibackend.develop.dto.openAi.request;

public record OpenAIChatPropertyDetailRequest(String type, String description) {

    public static OpenAIChatPropertyDetailRequest of(String type, String description) {
        return new OpenAIChatPropertyDetailRequest(type, description);
    }
}
