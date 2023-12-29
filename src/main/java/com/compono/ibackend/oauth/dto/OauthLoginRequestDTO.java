package com.compono.ibackend.oauth.dto;

public record OauthLoginRequestDTO(
        String provider,
        String code
) {
}
