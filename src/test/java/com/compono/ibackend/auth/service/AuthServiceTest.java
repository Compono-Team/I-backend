package com.compono.ibackend.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.compono.ibackend.auth.dto.response.AuthRefreshResponse;
import com.compono.ibackend.common.exception.CustomException;
import com.compono.ibackend.common.utils.jwt.JwtProvider;
import com.compono.ibackend.user.domain.User;
import com.compono.ibackend.user.dto.request.UserAddRequest;
import com.compono.ibackend.user.enumType.OauthProvider;
import com.compono.ibackend.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private JwtProvider jwtProvider;

    @Mock private UserRepository userRepository;

    @Mock HttpServletRequest httpServletRequest;

    @Mock HttpServletResponse httpServletResponse;

    @InjectMocks private AuthService authService;

    @Test
    void refresh() {
        String refreshTokenValue = UUID.randomUUID().toString();
        String email = "compono@test.com";
        Cookie cookie = new Cookie("refresh_token", refreshTokenValue);

        when(httpServletRequest.getCookies()).thenReturn(new Cookie[] {cookie});

        when(jwtProvider.getClaims(anyString())).thenReturn(createClaims(email, 60L));

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(createUser(email)));

        AuthRefreshResponse response = authService.refresh(httpServletRequest, httpServletResponse);

        assertThat(response.email()).isEqualTo(email);
    }

    @Test
    void refresh_exception_when_cookies_null() {
        when(httpServletRequest.getCookies()).thenReturn(null);

        assertThatThrownBy(() -> authService.refresh(httpServletRequest, httpServletResponse))
                .isInstanceOf(CustomException.class);
    }

    @Test
    void refresh_exception_when_refresh_token_expired() {
        String refreshTokenValue = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("refresh_token", refreshTokenValue);
        String email = "compono@test.com";

        when(jwtProvider.getClaims(anyString())).thenReturn(createClaims(email, -10L));

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(createUser(email)));

        when(httpServletRequest.getCookies()).thenReturn(new Cookie[] {cookie});

        assertThatThrownBy(() -> authService.refresh(httpServletRequest, httpServletResponse))
                .isInstanceOf(CustomException.class);
    }

    @Test
    void refresh_exception_when_email_not_exist() {
        String refreshTokenValue = UUID.randomUUID().toString();
        String email = "compono@test.com";
        Cookie cookie = new Cookie("refresh_token", refreshTokenValue);

        when(httpServletRequest.getCookies()).thenReturn(new Cookie[] {cookie});

        when(jwtProvider.getClaims(anyString())).thenReturn(createClaims(email, 60L));

        when(userRepository.findByEmail(email)).thenThrow(CustomException.class);

        assertThatThrownBy(() -> authService.refresh(httpServletRequest, httpServletResponse))
                .isInstanceOf(CustomException.class);
    }

    @Test
    void getClaims() {
        String accessToken = UUID.randomUUID().toString();
        String email = "compono@test.com";
        when(jwtProvider.getClaims(anyString())).thenReturn(createClaims(email, 60L));

        Claims claims = authService.getClaims(accessToken);

        assertThat(claims.getSubject()).isEqualTo(email);
    }

    private Claims createClaims(String email, Long expiredTime) {
        return Jwts.claims()
                .id("jti")
                .subject(email)
                .expiration(Date.from(Instant.now().plusSeconds(expiredTime)))
                .build();
    }

    private User createUser(String email) {
        UserAddRequest request =
                new UserAddRequest(
                        email, "compono", OauthProvider.KAKAO, UUID.randomUUID().toString(), true);
        return User.from(request);
    }
}
