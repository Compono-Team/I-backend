package com.compono.ibackend.oauth.controller;

import com.compono.ibackend.oauth.dto.request.OauthLoginRequest;
import com.compono.ibackend.oauth.dto.response.OauthLoginResponse;
import com.compono.ibackend.oauth.service.OauthService;
import com.compono.ibackend.user.enumType.OauthProvider;
import jakarta.servlet.http.HttpServletResponse;
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

    private final OauthService<OauthLoginRequest> kakaoOauthService;
    private final OauthService<OauthLoginRequest> googleOauthService;


    @GetMapping("/{provider}")
    public ResponseEntity<OauthLoginResponse> oauthLoginCallback(@PathVariable String provider,
                                                                 @RequestParam String code,
                                                                 HttpServletResponse httpServletResponse) {
        OauthLoginRequest oauthLoginRequestDTO = new OauthLoginRequest(provider, code);
        if (isProviderKakao(provider)) {
            OauthLoginResponse res = kakaoOauthService.login(oauthLoginRequestDTO, httpServletResponse);
            return ResponseEntity.ok().body(res);
        }

        if (isProviderGoogle(provider)) {
            OauthLoginResponse res = googleOauthService.login(oauthLoginRequestDTO, httpServletResponse);
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
