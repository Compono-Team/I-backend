package com.compono.ibackend.develop.dto.openAi.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record OpenAIChatRequest(
        String model,
        List<OpenAIChatMsgRequest> messages,
        List<OpenAIChatToolInfoRequest> tools,
        Boolean stream,
        @JsonProperty("tool_choice") String toolChoice) {

    public static OpenAIChatRequest of(
            String model, List<OpenAIChatMsgRequest> messages, boolean stream) {
        return new OpenAIChatRequest(model, messages, null, stream, null);
    }

    public static OpenAIChatRequest of(
            String model,
            List<OpenAIChatMsgRequest> messages,
            List<OpenAIChatToolInfoRequest> tools,
            String toolChoice) {
        return new OpenAIChatRequest(model, messages, tools, null, toolChoice);
    }
}
