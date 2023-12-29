package com.compono.ibackend.oauth.domain;

import com.compono.ibackend.oauth.dto.OauthLoginResponseDTO;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GoogleUserInfo implements OauthUserInfo {

    private final Map<String, Object> attributes;

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getNickName() {
        return (String) attributes.get("name");
    }

    public OauthLoginResponseDTO toDTO(boolean isRegistered) {
        return new OauthLoginResponseDTO(
                getProviderId(),
                getEmail(),
                getNickName(),
                isRegistered
        );
    }
}