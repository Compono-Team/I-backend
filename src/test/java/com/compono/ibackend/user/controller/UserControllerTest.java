package com.compono.ibackend.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.compono.ibackend.user.dto.request.UserAddRequest;
import com.compono.ibackend.user.dto.response.UserAddResponse;
import com.compono.ibackend.user.service.UserService;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("[컨트롤러] - 유저")
@WebMvcTest(UserController.class)
@AutoConfigureRestDocs
class UserControllerTest {

    @Autowired private MockMvc mvc;

    @MockBean private UserService UserService;

    @DisplayName("{POST} 회원등록 - 정상호출")
    @WithMockUser(
            username = "ADMIN",
            roles = {"SUPER"})
    @Test
    void addUser() throws Exception {
        JSONObject request = new JSONObject();
        request.put("email", "compono@test.com");
        request.put("nickname", "compono");
        request.put("oauthProvider", "KAKAO");
        request.put("oauthProviderUniqueKey", "1233543412");
        request.put("isAuthenticated", true);

        when(UserService.addUser(any(UserAddRequest.class))).thenReturn(createUserAddResponse());

        mvc.perform(
                        RestDocumentationRequestBuilders.post("/api/v1/users")
                                .with(csrf().asHeader())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(request.toString()))
                .andDo(print())
                .andDo(
                        document(
                                "create-user",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("email").type(STRING).description("이메일"),
                                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                                        fieldWithPath("oauthProvider")
                                                .type(STRING)
                                                .description("Oauth 제공자"),
                                        fieldWithPath("oauthProviderUniqueKey")
                                                .type(STRING)
                                                .description("Oauth 유니크 키"),
                                        fieldWithPath("isAuthenticated")
                                                .type(BOOLEAN)
                                                .description("인증 여부")),
                                responseFields(
                                        fieldWithPath("id").type(NUMBER).description("유저 id"),
                                        fieldWithPath("email").type(STRING).description("이메일"),
                                        fieldWithPath("nickname").type(STRING).description("닉네임"))))
                .andExpect(status().isCreated());
    }

    public UserAddResponse createUserAddResponse() {
        return new UserAddResponse(1L, "compono@test.com", "compono");
    }
}
