package com.compono.ibackend.oauth.domain;

public interface OauthUserInfo {

    String getProviderId();

    String getProvider();

    String getEmail();

    String getNickName();
}
