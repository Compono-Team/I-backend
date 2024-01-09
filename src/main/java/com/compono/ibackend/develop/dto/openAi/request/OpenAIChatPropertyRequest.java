package com.compono.ibackend.develop.dto.openAi.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpenAIChatPropertyRequest {

    private OpenAIChatPropertyDetailRequest address; // 이벤트 진행하는 위치
    private OpenAIChatPropertyDetailRequest date; // 이벤트 진행 날짜
    private OpenAIChatPropertyDetailRequest scheduleByRound; // 회차별 일정
    private OpenAIChatPropertyDetailRequest detail; // 이외 이벤트 세부 정보
}
