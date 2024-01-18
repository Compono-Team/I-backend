package com.compono.ibackend.user.dto.request;

import com.compono.ibackend.user.domain.User;
import com.compono.ibackend.user.enumType.OauthProvider;

public record UserAddRequest(
        String email,
        String nickname,
        OauthProvider oauthProvider,
        String oauthProviderUniqueKey,
        Boolean isAuthenticated
) {
    public User toEntity() {
        return User.from(this);
    }
}
