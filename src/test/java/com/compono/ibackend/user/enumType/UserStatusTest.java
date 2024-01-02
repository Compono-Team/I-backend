package com.compono.ibackend.user.enumType;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserStatusTest {

    @Test
    @DisplayName("values test")
    void values() {
        UserStatus[] values = UserStatus.values();
        assertThat(values.length).isEqualTo(3);
    }

    @Test
    @DisplayName("valueOf test")
    void valueOf() {
        UserStatus active = UserStatus.valueOf("ACTIVE");
        assertThat(active).isSameAs(UserStatus.ACTIVE);
    }
}