package com.compono.ibackend.oauth.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OauthLoginRequestDTOTest {

    @Test
    void provider() {
        OauthLoginRequestDTO dto = new OauthLoginRequestDTO("kakao", "random");
        assertThat(dto.provider()).isEqualTo("kakao");
    }

    @Test
    void code() {
        OauthLoginRequestDTO dto = new OauthLoginRequestDTO("kakao", "random");
        assertThat(dto.code()).isEqualTo("random");
    }
}