package com.compono.ibackend.oauth.domain;

import com.compono.ibackend.oauth.dto.response.OauthLoginResponse;
import com.compono.ibackend.user.enumType.OauthProvider;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KakaoUserInfo implements OauthUserInfo {

    private final Map<String, Object> attributes;


    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getProvider() {
        return OauthProvider.KAKAO.getValue();
    }

    @Override
    public String getEmail() {
        return (String) getKakaoAccount().get("email");
    }

    @Override
    public String getNickName() {
        return (String) getProfile().get("nickname");
    }

    public Map<String, Object> getKakaoAccount() {
        return (Map<String, Object>) attributes.get("kakao_account");
    }

    public Map<String, Object> getProfile() {
        return (Map<String, Object>) getKakaoAccount().get("profile");
    }

    public OauthLoginResponse from(boolean isRegistered) {
        return new OauthLoginResponse(
                getProviderId(),
                getEmail(),
                getNickName(),
                isRegistered
        );
    }
}
