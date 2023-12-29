package com.compono.ibackend.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.compono.ibackend.user.dto.UserAddResponseDTO;
import com.compono.ibackend.user.service.UserService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureRestDocs
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    @WithMockUser(username = "ADMIN", roles = {"SUPER"})
    void addUser() throws Exception {
        JSONObject request = new JSONObject();
        request.put("email", "test@gmail.com");
        request.put("nickname", "tester");
        request.put("oauthProvider", "GOOGLE");
        request.put("oauthProviderUniqueKey", "1234");

        when(userService.addUser(any()))
                .thenReturn(new UserAddResponseDTO("added"));

        mockMvc.perform(post("/api/v1/users")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(request.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andDo(MockMvcRestDocumentation.document("users",
                        Preprocessors.preprocessRequest(prettyPrint()),
                        Preprocessors.preprocessResponse(prettyPrint())))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}