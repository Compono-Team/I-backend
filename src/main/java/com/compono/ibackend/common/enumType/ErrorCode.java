package com.compono.ibackend.common.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // BAD_REQUEST 4000
    UNKNOWN(4000, "알 수 없는 에러가 발생했습니다."),
    INVALID_PASSWORD(4001, "유효하지 않은 비밀번호 입니다."),
    NOT_FOUND_PRE_RESERVATION_ID(4002, "요청한 사전예약 정보가 존재하지 않습니다."),
    ENCRYPTION_FAILED(4003, "암호화에 실패하셨습니다."),
    DECRYPTION_FAILED(4004, "복호화에 실패하셨습니다."),
    CRYPTOGRAPHY_FAILED(4005, "암복호화에 실패하셨습니다."),
    OPEN_API_REQUEST_FAIL(4006, "OPEN API 요청에 실패하였습니다"),
    INVALID_EVENTDATETIME(4007, "유효한지 않은 EventDateTime 값입니다."),
    DUPLICATED_FAILED(4008, "이미 데이터가 존재합니다."),

    SCHEDULE_END_TIME_BEFORE_START_TIME(4009, "종료 시간이 시작 시간보다 이전입니다."),
    INVALID_DATE_TIME_FORMAT(4010, "잘못된 날짜 형식입니다."),
    INVALID_SCHEDULE(4011, "유효하지 않은 스케줄 데이터 값입니다."),

    // UNAUTHORIZED 4100
    EXPIRED_TOKEN(4100, "만료된 토큰입니다."),
    NOT_EXIST_COOKIE(4101, "쿠키가 존재하지 않습니다."),
    COOKIE_EXPIRATION(4102, "만료된 리프레시 토큰입니다."),
    INVALID_OAUTH_PROVIDER(4103, "유효한 OAUTH 제공자가 아닙니다."),
    INVALID_TOKEN(4104, "유효한 토큰이 아닙니다."),

    // NOT_FOUND 4400
    NOT_FOUND_USER(4400, "유저 ID가 존재하지 않습니다."),
    NOT_FOUND_SCHEDULE_TIME(4401, "스케쥴 시간이 존재하지 않습니다."),
    NOT_FOUND_TAG(4402, "요청한 tag id가 존재하지 않습니다."),
    NOT_FOUND_USER_EMAIL(4403, "요청한 user email이 존재하지 않습니다."),
    NOT_FOUND_SCHEDULE(4404, "요청한 schedule id이 존재하지 않습니다.");

    private final int code;
    private final String msg;
}
