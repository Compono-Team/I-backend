package com.compono.ibackend.common.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 400
    UNKNOWN(4000, "알 수 없는 에러가 발생했습니다."),
    INVALID_PASSWORD(4001, "유효하지 않은 비밀번호 입니다."),
    OPEN_API_REQUEST_FAIL(4002, "OPEN API 요청에 실패하였습니다"),
    INVALID_EVENTDATETIME(4003, "유효한지 않은 EventDateTime 값입니다."),

    // 401
    EXPIRED_TOKEN(4010, "만료된 토큰입니다.");

    private final int code;
    private final String msg;
}
