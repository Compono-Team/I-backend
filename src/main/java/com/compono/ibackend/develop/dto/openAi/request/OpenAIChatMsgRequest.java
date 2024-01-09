package com.compono.ibackend.develop.dto.openAi.request;

public record OpenAIChatMsgRequest(String role, String content) {

    public static OpenAIChatMsgRequest of(String role, String content) {
        return new OpenAIChatMsgRequest(role, content);
    }
}
