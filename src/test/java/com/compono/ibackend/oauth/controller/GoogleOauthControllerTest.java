package com.compono.ibackend.oauth.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.compono.ibackend.oauth.dto.request.OauthLoginRequest;
import com.compono.ibackend.oauth.dto.response.OauthLoginResponse;
import com.compono.ibackend.oauth.service.OauthService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("[컨트롤러] - Oauth")
@WebMvcTest(OauthController.class)
@AutoConfigureRestDocs
class GoogleOauthControllerTest {

    @Autowired private MockMvc mvc;

    @MockBean private OauthService<OauthLoginRequest> googleOauthService;

    @DisplayName("{GET} Oauth 로그인 google - 정상호출")
    @Test
    @WithMockUser(
            username = "ADMIN",
            roles = {"SUPER"})
    void oauthLoginCallback() throws Exception {
        when(googleOauthService.login(any(OauthLoginRequest.class), any(HttpServletResponse.class)))
                .thenReturn(createOauthLoginResponse());

        mvc.perform(
                        RestDocumentationRequestBuilders.get("/api/v1/oauth/{provider}", "google")
                                .with(csrf().asHeader())
                                .param("code", UUID.randomUUID().toString()))
                .andDo(print())
                .andDo(
                        document(
                                "oauth-login-google",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("oauthProviderUniqueKey")
                                                .type(STRING)
                                                .description("유저 Oauth 유니크 키"),
                                        fieldWithPath("email")
                                                .type(STRING)
                                                .description("Oauth 등록 이메일"),
                                        fieldWithPath("nickname")
                                                .type(STRING)
                                                .description("Oauth 등록 닉네임"),
                                        fieldWithPath("isRegistered")
                                                .type(BOOLEAN)
                                                .description("회원가입 여부"))))
                .andExpect(status().isOk());
    }

    private OauthLoginResponse createOauthLoginResponse() {
        String oauthProviderUniqueKey = UUID.randomUUID().toString();
        String email = "compono@test.com";
        String nickname = "compono";

        return new OauthLoginResponse(oauthProviderUniqueKey, email, nickname, true);
    }
}
