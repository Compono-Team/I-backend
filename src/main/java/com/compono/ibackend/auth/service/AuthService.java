package com.compono.ibackend.auth.service;

import com.compono.ibackend.auth.dto.response.AuthRefreshResponse;
import com.compono.ibackend.common.enumType.ErrorCode;
import com.compono.ibackend.common.exception.CustomException;
import com.compono.ibackend.common.utils.jwt.JwtProvider;
import com.compono.ibackend.user.domain.User;
import com.compono.ibackend.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private static final String REFRESH_TOKEN_NAME = "refresh_token";
    private static final String JWT_PREFIX = "Bearer ";

    public AuthRefreshResponse refresh(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String refreshToken = getCookieFromHttpServletRequest(httpServletRequest);
        assert refreshToken != null;

        Claims claims = jwtProvider.getClaims(refreshToken);
        String email = claims.getSubject();

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () ->
                                        new CustomException(
                                                HttpStatus.BAD_REQUEST, ErrorCode.NOT_FOUND_USER));

        Date now = Date.from(Instant.now());
        if (claims.getExpiration().before(now)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.COOKIE_EXPIRATION);
        }

        Long userId = user.getId();
        String newAccessTokenValue = jwtProvider.createAccessToken(user.getEmail());
        String newAccessToken = JWT_PREFIX + newAccessTokenValue;
        httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, newAccessToken);

        return AuthRefreshResponse.of(userId, email);
    }

    public Claims getClaims(String accessToken) {
        return jwtProvider.getClaims(accessToken);
    }

    private String getCookieFromHttpServletRequest(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getCookies() == null) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, ErrorCode.NOT_EXIST_COOKIE);
        }

        Cookie cookie =
                Arrays.stream(httpServletRequest.getCookies())
                        .filter(c -> c.getName().equals(REFRESH_TOKEN_NAME))
                        .findFirst()
                        .orElseThrow(
                                () ->
                                        new CustomException(
                                                HttpStatus.BAD_REQUEST,
                                                ErrorCode.NOT_EXIST_COOKIE));
        return cookie.getValue();
    }
}
