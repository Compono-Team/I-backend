package com.compono.ibackend.oauth.service;

import com.compono.ibackend.oauth.domain.GoogleUserInfo;
import com.compono.ibackend.oauth.dto.OauthLoginRequestDTO;
import com.compono.ibackend.oauth.dto.OauthLoginResponseDTO;
import com.compono.ibackend.oauth.dto.OauthTokenResponseDTO;
import com.compono.ibackend.user.repository.UserRepository;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleOauthService implements OauthService<OauthLoginRequestDTO> {

    private final InMemoryClientRegistrationRepository clientRegistrationRepository;
    private final UserRepository userRepository;

    @Override
    public OauthLoginResponseDTO login(OauthLoginRequestDTO param) {

        ClientRegistration provider = clientRegistrationRepository.findByRegistrationId(
                param.provider());
        OauthTokenResponseDTO oauthToken = getOauthToken(param.code(), provider);

        GoogleUserInfo userFromGoogle = getUserInfoFromGoogle(param.provider(), oauthToken, provider);
        String oauthProviderUniqueKey = userFromGoogle.getProviderId();
        boolean isRegistered = userRepository.existsUserByOauthProviderUniqueKey(oauthProviderUniqueKey);
        return userFromGoogle.toDTO(isRegistered);
    }

    private OauthTokenResponseDTO getOauthToken(String code, ClientRegistration provider) {
        return WebClient.create()
                .post()
                .uri(provider.getProviderDetails().getTokenUri())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(createOauthRequestBody(code, provider))
                .retrieve()
                .bodyToMono(OauthTokenResponseDTO.class)
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

    private GoogleUserInfo getUserInfoFromGoogle(String providerName, OauthTokenResponseDTO oauthToken,
                                                 ClientRegistration provider) {
        if (!providerName.equals("google")) {
            throw new RuntimeException("invalid provider name");
        }
        Map<String, Object> userAttributes = getUserAttribute(provider, oauthToken);
        return new GoogleUserInfo(userAttributes);
    }

    private Map<String, Object> getUserAttribute(ClientRegistration provider, OauthTokenResponseDTO oauthToken) {
        return WebClient.create()
                .get()
                .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
                .headers(header -> header.setBearerAuth(String.valueOf(oauthToken.accessToken())))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }
}
