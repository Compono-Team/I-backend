package com.compono.ibackend.common.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HTTPHeaderType {
    CONTENT_TYPE("1", "Content-Type"),
    AUTHORIZATION("2", "Authorization"),

    UNKNOWN("99", "UNKNOWN");

    private final String code;
    private final String key;
}
