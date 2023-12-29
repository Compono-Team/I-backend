package com.compono.ibackend.oauth.dto;

public record OauthLoginResponseDTO(
        String oauthProviderUniqueKey,
        String email,
        String nickname,
        boolean isRegistered
) {
}
