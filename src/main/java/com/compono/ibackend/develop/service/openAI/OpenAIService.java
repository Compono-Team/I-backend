package com.compono.ibackend.develop.service.openAI;

import com.compono.ibackend.common.enumType.HTTPHeaderType;
import com.compono.ibackend.develop.dto.openAi.request.OpenAIChatMsgRequest;
import com.compono.ibackend.develop.dto.openAi.request.OpenAIChatParameterRequest;
import com.compono.ibackend.develop.dto.openAi.request.OpenAIChatPropertyRequest;
import com.compono.ibackend.develop.dto.openAi.request.OpenAIChatRequest;
import com.compono.ibackend.develop.dto.openAi.request.OpenAIChatToolInfoRequest;
import com.compono.ibackend.develop.dto.openAi.request.OpenAiChatFunctionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OpenAIService {

    private static final String OPEN_AI_CHAT_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String OPEN_AI_MODEL_NAME = "gpt-4";

    private static final String SCHEDULE_FUNCTION_NAME = "get_event_schedule";
    private static final String SCHEDULE_FUNCTION_DESCRIPTION =
        "Get schedule information for the event you asked";

    @Value("${spring.api-key.open-ai.open-ai-api.key}")
    private String openAiApiKey;

    public HttpHeaders getOpenAiHeader() {
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HTTPHeaderType.AUTHORIZATION.getKey(), openAiApiKey);

        return headers;
    }

    public void getChatResponse(String question) {
        // 1. 헤더 구성
        HttpHeaders headers = getOpenAiHeader();

        // 2. Request body 구성
        OpenAIChatMsgRequest openAIChatMsgRequest = new OpenAIChatMsgRequest("user", question);
        OpenAIChatPropertyRequest propertyRequest = OpenAIChatPropertyRequest.builder().build();
        OpenAIChatParameterRequest parameterRequest =
            OpenAIChatParameterRequest.builder()
                .type("object")
                .properties(propertyRequest)
                .build();
        OpenAiChatFunctionRequest functionRequest =
            OpenAiChatFunctionRequest.builder()
                .name(SCHEDULE_FUNCTION_NAME)
                .description(SCHEDULE_FUNCTION_DESCRIPTION)
                .parameters(parameterRequest)
                .build();
        OpenAIChatToolInfoRequest toolInfoRequest =
            OpenAIChatToolInfoRequest.builder()
                .type("function")
                .function(functionRequest)
                .build();
        OpenAIChatRequest openAIChatRequest =
            OpenAIChatRequest.builder()
                .model(OPEN_AI_MODEL_NAME)
                .message(openAIChatMsgRequest)
                .tools(toolInfoRequest)
                .toolChoice("auto")
                .build();
    }
}
