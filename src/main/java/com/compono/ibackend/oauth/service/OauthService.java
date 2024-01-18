package com.compono.ibackend.oauth.service;

import com.compono.ibackend.oauth.dto.response.OauthLoginResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface OauthService<T> {
    OauthLoginResponse login(T oauthLoginRequestDTO, HttpServletResponse httpServletResponse);
}
