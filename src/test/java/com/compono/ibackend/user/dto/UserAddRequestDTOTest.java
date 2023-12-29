package com.compono.ibackend.user.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.compono.ibackend.user.enumType.OauthProvider;
import org.junit.jupiter.api.Test;

class UserAddRequestDTOTest {

    @Test
    void email() {
        UserAddRequestDTO dto = new UserAddRequestDTO("test@gmail.com", "tester", OauthProvider.GOOGLE, "1234");
        assertThat(dto.email()).isEqualTo("test@gmail.com");
    }

    @Test
    void nickname() {
        UserAddRequestDTO dto = new UserAddRequestDTO("test@gmail.com", "tester", OauthProvider.GOOGLE, "1234");
        assertThat(dto.nickname()).isEqualTo("tester");
    }

    @Test
    void oauthProvider() {
        UserAddRequestDTO dto = new UserAddRequestDTO("test@gmail.com", "tester", OauthProvider.GOOGLE, "1234");
        assertThat(dto.oauthProvider()).isSameAs(OauthProvider.GOOGLE);
    }

    @Test
    void oauthProviderUniqueKey() {
        UserAddRequestDTO dto = new UserAddRequestDTO("test@gmail.com", "tester", OauthProvider.GOOGLE, "1234");
        assertThat(dto.oauthProviderUniqueKey()).isEqualTo("1234");
    }
}