package com.compono.ibackend.oauth.service;

import com.compono.ibackend.oauth.dto.OauthLoginResponseDTO;

public interface OauthService<T> {
    OauthLoginResponseDTO login(T param);
}
