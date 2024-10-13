package com.compono.ibackend.model.user.dto.request;

import com.compono.ibackend.model.user.domain.User;
import com.compono.ibackend.model.user.enumType.OauthProvider;

public record UserAddRequest(
        String email,
        String nickname,
        OauthProvider oauthProvider,
        String oauthProviderUniqueKey,
        Boolean isAuthenticated) {
    public User toEntity() {
        return User.from(this);
    }
}
