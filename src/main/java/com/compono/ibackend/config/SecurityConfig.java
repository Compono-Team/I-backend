package com.compono.ibackend.config;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private static final String[] DEFAULT_WHITELIST = {"/status", "/images/**", "/error/**"};

    private static final String[] DEVELOP_TEST_PATH = {
        "api/develop/**",
    };

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                request ->
                        request.dispatcherTypeMatchers(DispatcherType.FORWARD)
                                .permitAll()
                                .requestMatchers(DEFAULT_WHITELIST)
                                .permitAll()
                                .requestMatchers(DEVELOP_TEST_PATH)
                                .permitAll()
                                .anyRequest()
                                .authenticated());
        return http.build();
    }
}
