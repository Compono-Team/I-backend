package com.compono.ibackend.develop.dto.openAi.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
@AllArgsConstructor
public class OpenAIChatMsgRequest {

    private String role;
    private String content;
}
