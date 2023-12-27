package com.compono.ibackend.develop.dto.openAi.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
@AllArgsConstructor
public class OpenAIChatRequest {

    private String model;
    private OpenAIChatMsgRequest message;
    private OpenAIChatToolInfoRequest tools;

    @JsonProperty("tool_choice")
    private String toolChoice;
}
