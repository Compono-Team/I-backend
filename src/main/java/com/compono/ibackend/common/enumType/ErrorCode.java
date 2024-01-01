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

    // 401
    EXPIRED_TOKEN(4010, "만료된 토큰입니다.");

    private final int code;
    private final String msg;
}
