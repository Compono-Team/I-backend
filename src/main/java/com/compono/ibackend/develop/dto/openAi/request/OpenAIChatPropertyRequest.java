package com.compono.ibackend.develop.dto.openAi.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
@AllArgsConstructor
public class OpenAIChatPropertyRequest {

    private Object location; // 이벤트 진행하는 위치
    private Object date; // 이벤트 진행 날짜
    private Object scheduleByRound; // 회차별 일정
    private Object detail; // 이외 이벤트 세부 정보
    private List<String> required; // ["location", "date"];
}
