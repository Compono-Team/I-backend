package com.compono.ibackend.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OauthTokenResponseDTO(
        @JsonProperty(value = "token_type")
        String tokeType,

        @JsonProperty(value = "access_token")
        String accessToken,

        @JsonProperty(value = "expires_in")
        Integer expiresIn,

        @JsonProperty(value = "refresh_token")
        String refreshToken,

        @JsonProperty(value = "refresh_token_expires_in")
        Integer refreshTokenExpiresIn
) {
}
