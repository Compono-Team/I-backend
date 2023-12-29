package com.compono.ibackend.oauth.controller;

import com.compono.ibackend.oauth.dto.OauthLoginRequestDTO;
import com.compono.ibackend.oauth.dto.OauthLoginResponseDTO;
import com.compono.ibackend.oauth.service.OauthService;
import com.compono.ibackend.user.enumType.OauthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/oauth")
public class OauthController {

    private final OauthService<OauthLoginRequestDTO> kakaoOauthService;
    private final OauthService<OauthLoginRequestDTO> googleOauthService;

    @GetMapping("/{provider}")
    public ResponseEntity<OauthLoginResponseDTO> oauthLoginCallback(@PathVariable String provider,
                                                                    @RequestParam String code) {
        OauthLoginRequestDTO oauthLoginRequestDTO = new OauthLoginRequestDTO(provider, code);
        if (isProviderKakao(provider)) {
            OauthLoginResponseDTO res = kakaoOauthService.login(oauthLoginRequestDTO);
            return ResponseEntity.ok().body(res);
        }

        if (isProviderGoogle(provider)) {
            OauthLoginResponseDTO res = googleOauthService.login(oauthLoginRequestDTO);
            return ResponseEntity.ok().body(res);
        }

        return null;
    }

    private boolean isProviderGoogle(String provider) {
        return provider.equals(OauthProvider.GOOGLE.getValue());
    }

    private boolean isProviderKakao(String provider) {
        return provider.equals(OauthProvider.KAKAO.getValue());
    }
}
