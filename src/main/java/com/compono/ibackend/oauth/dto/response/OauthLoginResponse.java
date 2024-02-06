package com.compono.ibackend.oauth.dto.response;

public record OauthLoginResponse(
        String oauthProviderUniqueKey, String email, String nickname, boolean isRegistered) {}
