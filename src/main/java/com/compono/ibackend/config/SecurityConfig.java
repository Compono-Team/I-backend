package com.compono.ibackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private static final String[] DEFAULT_WHITELIST = {"/status", "/images/**", "/error/**", "/api/v1/oauth/**"};

    private static final String[] DEVELOP_TEST_PATH = {"api/develop/**", "/api/develop/**"};

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        // todo : spring security 인가에 따른 접근권한 설정 필요
                        request -> request.anyRequest().permitAll());
        return http.build();
    }
}
