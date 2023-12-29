package com.compono.ibackend.user.dto;

import com.compono.ibackend.user.enumType.OauthProvider;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserAddRequestDTO(
        @Email
        String email,
        @NotEmpty
        String nickname,
        @NotEmpty
        OauthProvider oauthProvider,
        @NotEmpty
        String oauthProviderUniqueKey
) {
}
