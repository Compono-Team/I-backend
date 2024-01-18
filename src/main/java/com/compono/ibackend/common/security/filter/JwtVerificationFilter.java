package com.compono.ibackend.common.security.filter;

import com.compono.ibackend.auth.service.AuthService;
import com.compono.ibackend.common.enumType.ErrorCode;
import com.compono.ibackend.common.security.impl.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

    private static final String JWT_PREFIX = "Bearer ";
    private final AuthService authService;

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String bearerToken = getAuthenticationHeaderValue(request);
        return bearerToken == null || !bearerToken.startsWith(JWT_PREFIX);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            setAuthenticationToContext(request);
        } catch (JwtException e) {
            request.setAttribute("exception", ErrorCode.INVALID_TOKEN);
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthenticationToContext(HttpServletRequest request) {
        Authentication authentication = createAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Authentication createAuthentication(HttpServletRequest request) {
        String accessToken = getAuthenticationHeaderValue(request);
        accessToken = accessToken.substring(JWT_PREFIX.length());

        Claims claims = authService.getClaims(accessToken);
        UserDetailsImpl userDetails = new UserDetailsImpl(claims);

        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    private String getAuthenticationHeaderValue(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }
}
