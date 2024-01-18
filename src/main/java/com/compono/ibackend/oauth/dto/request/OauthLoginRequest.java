package com.compono.ibackend.oauth.dto.request;

public record OauthLoginRequest(
        String provider,
        String code
) {
}
