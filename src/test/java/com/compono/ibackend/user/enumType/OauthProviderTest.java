package com.compono.ibackend.user.enumType;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OauthProviderTest {

    @Test
    void getValue() {
        String value = OauthProvider.GOOGLE.getValue();
        assertThat(value).isEqualTo("google");
    }

    @Test
    void values() {
        OauthProvider[] values = OauthProvider.values();
        assertThat(values.length).isEqualTo(3);
    }

    @Test
    void valueOf() {
        OauthProvider oauthProvider = OauthProvider.valueOf("GOOGLE");
        assertThat(oauthProvider).isSameAs(OauthProvider.GOOGLE);
    }
}