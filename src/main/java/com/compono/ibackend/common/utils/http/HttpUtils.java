package com.compono.ibackend.common.utils.http;

import com.compono.ibackend.constants.CommonConstants;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.ResponseCookie;

public class HttpUtils {

    public static ResponseCookie createCookie(String refreshToken) {
        return ResponseCookie.from(CommonConstants.REFRESH_TOKEN_NAME, refreshToken)
                .maxAge(CommonConstants.COOKIE_MAX_AGE)
                .domain("axyz")
                .path("/")
                .secure(true)
                .sameSite(SameSite.NONE.name())
                .httpOnly(true)
                .build();
    }
}
