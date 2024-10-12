package com.compono.ibackend.config;

import com.compono.ibackend.auth.service.AuthService;
import com.compono.ibackend.common.security.filter.JwtVerificationFilter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final AuthService authService;

    private static final String[] DEFAULT_WHITELIST = {
        "/status",
        "/images/**",
        "/error/**",
        "/api/v1/oauth/**",
        "/api/v1/users/**",
    };

    private static final String[] DEVELOP_TEST_PATH = {"api/develop/**", "/api/develop/**"};

    @Value("${white-list}")
    private final List<String> ENV_WHITELIST;

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        String[] concatWhitelist = Stream.concat(
            Stream.of(DEFAULT_WHITELIST),
            ENV_WHITELIST.stream()
        ).toArray(String[]::new);

        http.csrf(AbstractHttpConfigurer::disable)
            .cors(customCorsConfig())
            .authorizeHttpRequests(
                request ->
                    request.requestMatchers(concatWhitelist)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
            .headers(
                header ->
                    header.frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::sameOrigin))
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(
                new JwtVerificationFilter(authService),
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public Customizer<CorsConfigurer<HttpSecurity>> customCorsConfig() {
        return httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(List.of("*"));
        corsConfiguration.setAllowedOrigins(
            List.of("http://localhost:3000", "https://www.axyz.today"));
        corsConfiguration.setAllowedMethods(
            Arrays.asList("POST", "GET", "DELETE", "PATCH", "PUT", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addExposedHeader("*");
        corsConfiguration.addExposedHeader(HttpHeaders.AUTHORIZATION);
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
