package com.compono.ibackend.develop.service.openAI;

import com.compono.ibackend.common.utils.HTTPUtils;
import com.compono.ibackend.develop.aop.annotation.TimeTrace;
import com.compono.ibackend.develop.dto.openAi.request.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OpenAIService {

    private static final String OPEN_AI_CHAT_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String OPEN_AI_MODEL_NAME = "gpt-4-1106-preview";

    private static final String SCHEDULE_FUNCTION_NAME = "get_event_schedule";
    private static final String SCHEDULE_FUNCTION_DESCRIPTION =
            "Get schedule information for the event you asked";

    @Value("${spring.api-key.open-ai.open-ai-api.key}")
    private String openAiApiKey;

    public HttpHeaders getOpenAiHeader() {
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        return headers;
    }

    @TimeTrace
    public Object getChatResponse(String question) {
        // 1. 헤더 구성
        HttpHeaders headers = getOpenAiHeader();

        // 2. Request body 구성
        List<OpenAIChatMsgRequest> messages = new ArrayList<>();
        messages.add(OpenAIChatMsgRequest.of("user", question));

        List<String> requiredParameter = new ArrayList<>();
        requiredParameter.add("date");
        requiredParameter.add("address");

        List<OpenAIChatToolInfoRequest> tools = new ArrayList<>();

        // "string number integer object array boolean null"
        OpenAIChatPropertyRequest propertyRequest =
                OpenAIChatPropertyRequest.builder()
                        .address(
                                OpenAIChatPropertyDetailRequest.of(
                                        "string",
                                        "the address of the place where the event is taking place"))
                        .date(
                                OpenAIChatPropertyDetailRequest.of(
                                        "string",
                                        "Date of progress of an event in timestamp format"))
                        .scheduleByRound(
                                OpenAIChatPropertyDetailRequest.of(
                                        "string", "Information by episode of the event"))
                        .detail(
                                OpenAIChatPropertyDetailRequest.of(
                                        "string", "details of the event"))
                        .build();
        OpenAIChatParameterRequest parameterRequest =
                OpenAIChatParameterRequest.builder()
                        .type("object")
                        .properties(propertyRequest)
                        .required(requiredParameter)
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

        tools.add(toolInfoRequest);

        OpenAIChatRequest openAIChatRequest =
                OpenAIChatRequest.of(OPEN_AI_MODEL_NAME, messages, tools, "auto");

        ResponseEntity<Map> reseponse =
                HTTPUtils.post(OPEN_AI_CHAT_API_URL, headers, openAIChatRequest, Map.class);

        return reseponse.getBody().get("choices");
    }

    @TimeTrace
    public Object getChatSimpleResponse(String question) {
        // 1. 헤더 구성
        HttpHeaders headers = getOpenAiHeader();

        // 2. 바디 구성
        List<OpenAIChatMsgRequest> messages = new ArrayList<>();
        messages.add(OpenAIChatMsgRequest.of("user", question));
        OpenAIChatRequest openAIChatRequest =
                OpenAIChatRequest.of(OPEN_AI_MODEL_NAME, messages, false);

        ResponseEntity<Map> reseponse =
                HTTPUtils.post(OPEN_AI_CHAT_API_URL, headers, openAIChatRequest, Map.class);

        return reseponse.getBody();
    }
}
