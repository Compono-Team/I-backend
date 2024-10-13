package com.compono.ibackend.oauth.domain;

import com.compono.ibackend.oauth.dto.response.OauthLoginResponse;
import com.compono.ibackend.model.user.enumType.OauthProvider;
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
        return OauthProvider.GOOGLE.getValue();
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getNickName() {
        return (String) attributes.get("name");
    }

    public OauthLoginResponse from(boolean isRegistered) {
        return new OauthLoginResponse(getProviderId(), getEmail(), getNickName(), isRegistered);
    }
}
