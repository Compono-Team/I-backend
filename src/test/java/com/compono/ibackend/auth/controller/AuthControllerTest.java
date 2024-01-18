package com.compono.ibackend.auth.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.compono.ibackend.auth.dto.response.AuthRefreshResponse;
import com.compono.ibackend.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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

@DisplayName("[컨트롤러] - 인증")
@WebMvcTest(AuthController.class)
@AutoConfigureRestDocs
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthService authService;


    @DisplayName("{POST} Access Token 리프레시 - 정상호출")
    @Test
    @WithMockUser(
            username = "ADMIN",
            roles = {"SUPER"})
    void refresh() throws Exception {
        when(authService.refresh(any(HttpServletRequest.class), any(HttpServletResponse.class)))
                .thenReturn(createAuthRefreshResponse());

        mvc.perform(
                        RestDocumentationRequestBuilders.post("/api/v1/auth/refresh")
                                .with(csrf().asHeader())
                                .cookie(new Cookie("refresh-token", UUID.randomUUID().toString())))
                .andDo(print())
                .andDo(
                        document(
                                "auth-refresh",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("id")
                                                .type(NUMBER)
                                                .description("유저 id"),
                                        fieldWithPath("email")
                                                .type(STRING)
                                                .description("유저 이메일")
                                )
                        )
                )
                .andExpect(status().isOk());
    }

    private AuthRefreshResponse createAuthRefreshResponse() {
        Long id = 1L;
        String email = "test@gmail.com";
        return new AuthRefreshResponse(id, email);
    }
}