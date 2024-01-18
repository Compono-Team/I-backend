package com.compono.ibackend.oauth.service;

import com.compono.ibackend.common.enumType.ErrorCode;
import com.compono.ibackend.common.exception.CustomException;
import com.compono.ibackend.common.utils.jwt.JwtProvider;
import com.compono.ibackend.oauth.domain.GoogleUserInfo;
import com.compono.ibackend.oauth.dto.OauthTokenDTO;
import com.compono.ibackend.oauth.dto.request.OauthLoginRequest;
import com.compono.ibackend.oauth.dto.response.OauthLoginResponse;
import com.compono.ibackend.user.domain.User;
import com.compono.ibackend.user.enumType.OauthProvider;
import com.compono.ibackend.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleOauthService implements OauthService<OauthLoginRequest> {

    private final InMemoryClientRegistrationRepository clientRegistrationRepository;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    private static final String REFRESH_TOKEN_NAME = "refresh_token";
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 200;

    @Override
    public OauthLoginResponse login(OauthLoginRequest param, HttpServletResponse httpServletResponse) {

        ClientRegistration provider = clientRegistrationRepository.findByRegistrationId(
                param.provider());
        OauthTokenDTO oauthToken = getOauthToken(param.code(), provider);

        GoogleUserInfo googleUser = getUserInfoFromGoogle(param.provider(), oauthToken, provider);
        String oauthProviderUniqueKey = googleUser.getProviderId();

        Optional<User> optionalUser =
                userRepository.findByOauthProviderUniqueKey(oauthProviderUniqueKey);
        boolean isRegistered;
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String accessToken = jwtProvider.createAccessToken(user.getEmail());
            httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, accessToken);

            String refreshToken = jwtProvider.createRefreshToken(user.getEmail());
            ResponseCookie cookie = createCookie(refreshToken);
            httpServletResponse.setHeader("Set-Cookie", cookie.toString());

            isRegistered = true;
        } else {
            // 등록된 유저가 아닌 경우: 프론트에서 Oauth 유저 정보를 기반으로 회원가입 페이지로 랜딩
            isRegistered = false;
        }

        return googleUser.from(isRegistered);
    }

    private OauthTokenDTO getOauthToken(String code, ClientRegistration provider) {
        return WebClient.create()
                .post()
                .uri(provider.getProviderDetails().getTokenUri())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(createOauthRequestBody(code, provider))
                .retrieve()
                .bodyToMono(OauthTokenDTO.class)
                .block();
    }

    private MultiValueMap<String, String> createOauthRequestBody(String code, ClientRegistration provider) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", provider.getRedirectUri());
        formData.add("client_secret", provider.getClientSecret());
        formData.add("client_id", provider.getClientId());
        return formData;
    }

    private GoogleUserInfo getUserInfoFromGoogle(String providerName, OauthTokenDTO oauthToken,
                                                 ClientRegistration provider) {
        if (!providerName.equals(OauthProvider.GOOGLE.getValue())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_OAUTH_PROVIDER);
        }
        Map<String, Object> userAttributes = getUserAttribute(provider, oauthToken);
        return new GoogleUserInfo(userAttributes);
    }

    private Map<String, Object> getUserAttribute(ClientRegistration provider, OauthTokenDTO oauthToken) {
        return WebClient.create()
                .get()
                .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
                .headers(header -> header.setBearerAuth(String.valueOf(oauthToken.accessToken())))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }

    private ResponseCookie createCookie(String refreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN_NAME, refreshToken)
                .maxAge(COOKIE_MAX_AGE)
                .domain("compono")
                .path("/")
                .secure(true)
                .sameSite(SameSite.NONE.name())
                .httpOnly(true)
                .build();
    }
}
