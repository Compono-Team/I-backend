package com.compono.ibackend.user.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OauthProvider {
    GOOGLE("google"),
    KAKAO("kakao"),
    APPLE("apple");

    private final String value;
}
