package com.compono.ibackend.common.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.access-token-expiration-minute}")
    private long accessTokenExpirationMinute;

    @Value("${jwt.refresh-token-expiration-minute}")
    private long refreshTokenExpirationMinute;

    // Claim : JWT 에 넣을 내용(eg body, 부가적으로 사용할 정보를 넣을 수 있음)
    private Map<String, Object> createClaims(String username) {
        return Map.of("username", username);
    }

    public String createAccessToken(String username) {
        Instant now = Instant.now();
        Map<String, Object> claims = createClaims(username);
        return Jwts.builder()
                .issuer(issuer)
                .subject(username)
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(accessTokenExpirationMinute, ChronoUnit.MINUTES)))
                .signWith(getSecretKey())
                .compact();
    }

    public String createRefreshToken(String username) {
        Instant now = Instant.now();
        return Jwts.builder()
                .issuer(issuer)
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(refreshTokenExpirationMinute, ChronoUnit.MINUTES)))
                .signWith(getSecretKey())
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(this.secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
