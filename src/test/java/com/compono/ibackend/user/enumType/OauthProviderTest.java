package com.compono.ibackend.user.enumType;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OauthProviderTest {

    @Test
    @DisplayName("values test")
    void values() {
        OauthProvider[] values = OauthProvider.values();
        assertThat(values.length).isEqualTo(3);
    }

    @Test
    @DisplayName("valueOf test")
    void valueOf() {
        OauthProvider oauthProvider = OauthProvider.valueOf("GOOGLE");
        assertThat(oauthProvider).isSameAs(OauthProvider.GOOGLE);
    }
}