package com.compono.ibackend.common.utils.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import io.jsonwebtoken.Claims;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("JWT 발급, 파싱 테스트")
@SpringBootTest(
        classes = {JwtProvider.class},
        properties = {
            "spring.jwt.secret=sdjkjskjdkjskdjkswdnlkqwnlfkdnlkqwnfkqwnf",
            "spring.jwt.issuer=compono",
            "spring.jwt.access-token-expiration-minute=30",
            "spring.jwt.refresh-token-expiration-minute=7200"
        })
class JwtProviderTest {

    @Autowired private JwtProvider jwtProvider;

    @Test
    void createAccessToken() {
        String email = "test@gmail.com";
        String accessToken = jwtProvider.createAccessToken(email);

        assertThat(accessToken).isNotNull();
    }

    @Test
    void createRefreshToken() {
        String email = "test@gmail.com";
        String refreshToken = jwtProvider.createRefreshToken(email);

        assertThat(refreshToken).isNotNull();
    }

    @Test
    void getClaims() {
        Instant now = Instant.now();
        String email1 = "test@gmail.com";
        String accessToken = jwtProvider.createAccessToken(email1);

        Claims claims = jwtProvider.getClaims(accessToken);
        String subject = claims.getSubject();

        String email2 = (String) claims.get("email");

        assertThat(subject).isEqualTo(email1);
        assertThat(email1).isEqualTo(email2);
        assertThat(claims.getExpiration()).isBefore(now.plus(7199, ChronoUnit.MINUTES));
    }
}
