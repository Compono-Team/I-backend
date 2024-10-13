package com.compono.ibackend.model.user.dto.response;

import com.compono.ibackend.model.user.domain.User;

public record UserAddResponse(Long id, String email, String nickname) {
    public static UserAddResponse from(User user) {
        return new UserAddResponse(user.getId(), user.getEmail(), user.getNickname());
    }
}
