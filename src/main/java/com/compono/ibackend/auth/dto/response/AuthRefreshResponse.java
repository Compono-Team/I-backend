package com.compono.ibackend.auth.dto.response;

public record AuthRefreshResponse(Long id, String email) {
    public static AuthRefreshResponse of(Long userId, String email) {
        return new AuthRefreshResponse(userId, email);
    }
}
