package com.compono.ibackend.user.dto.request;

import com.compono.ibackend.common.annotation.EnumClass;
import com.compono.ibackend.user.domain.User;
import com.compono.ibackend.user.enumType.OauthProvider;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public record UserAddRequest(
        @Email String email,
        @NotBlank @Max(200) String nickname,
        @EnumClass(value = OauthProvider.class) OauthProvider oauthProvider,
        @NotBlank String oauthProviderUniqueKey) {

    public User toEntity() {
        return User.from(this);
    }
}
