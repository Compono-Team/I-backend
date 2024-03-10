package com.compono.ibackend.common.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 400
    UNKNOWN(4000, "알 수 없는 에러가 발생했습니다."),
    INVALID_PASSWORD(4001, "유효하지 않은 비밀번호 입니다."),
    NOT_FOUND_PRE_RESERVATION_ID(4002, "요청한 사전예약 정보가 존재하지 않습니다."),
    ENCRYPTION_FAILED(4003, "암호화에 실패하셨습니다."),
    DECRYPTION_FAILED(4004, "복호화에 실패하셨습니다."),
    CRYPTOGRAPHY_FAILED(4005, "암복호화에 실패하셨습니다."),
    OPEN_API_REQUEST_FAIL(4006, "OPEN API 요청에 실패하였습니다"),
    INVALID_EVENTDATETIME(4007, "유효한지 않은 EventDateTime 값입니다."),
    DUPLICATED_FAILED(4008, "이미 데이터가 존재합니다."),
    NOT_FOUND_USER_ID(4009, "유저 ID가 존재하지 않습니다."),
    INVALID_SCHEDULE(4010, "유효하지 않은 스케줄 데이터 값입니다."),
    NOT_FOUND_TAG_ID(4011, "요청한 tag id가 존재하지 않습니다."),
    NOT_FOUND_USER_EMAIL(4012, "요청한 user email이 존재하지 않습니다."),
    NOT_FOUND_SCHEDULE_ID(4012, "요청한 schedule id이 존재하지 않습니다."),

    // 401
    EXPIRED_TOKEN(4010, "만료된 토큰입니다."),
    NOT_EXIST_COOKIE(4011, "쿠키가 존재하지 않습니다."),
    COOKIE_EXPIRATION(4012, "만료된 리프레시 토큰입니다."),
    INVALID_OAUTH_PROVIDER(4013, "유효한 OAUTH 제공자가 아닙니다."),
    INVALID_TOKEN(4014, "유효한 토큰이 아닙니다.");

    private final int code;
    private final String msg;
}
