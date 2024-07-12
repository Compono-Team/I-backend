package com.compono.ibackend.reservation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.compono.ibackend.reservation.dto.request.BetaReservationRequest;
import com.compono.ibackend.reservation.dto.response.BetaReservationResponse;
import com.compono.ibackend.reservation.service.BetaReservationService;
import java.time.LocalDateTime;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("[컨트롤러] - 베타테스트 신청")
@WebMvcTest(BetaReservationController.class)
@AutoConfigureRestDocs
class BetaReservationControllerTest {

    private final MockMvc mvc;

    @MockBean private BetaReservationService betaReservationService;

    BetaReservationControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[POST] 베타테스트 신청 - 정상호출")
    @Test
    @WithMockUser(
            username = "ADMIN",
            roles = {"SUPER"})
    void addBetaReservation() throws Exception {
        // given
        JSONObject request = new JSONObject();
        request.put("email", "compono@test.com");
        request.put("name", "compono");
        request.put("phoneNumber", "010-1234-1234");

        // when
        when(betaReservationService.addBetaReservation(any(BetaReservationRequest.class)))
                .thenReturn(createBetaReservationResponse());

        // then
        mvc.perform(
                        RestDocumentationRequestBuilders.post("/api/v1/beta-test-reservation")
                                .with(csrf().asHeader())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(request.toString()))
                .andDo(print())
                .andDo(
                        document(
                                "create-betaReservation",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("email").type(STRING).description("이메일"),
                                        fieldWithPath("name").type(STRING).description("이름"),
                                        fieldWithPath("phoneNumber")
                                                .type(STRING)
                                                .description("전화번호")),
                                responseFields(
                                        fieldWithPath("id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("생성된 사전예약 id"),
                                        fieldWithPath("email").type(STRING).description("이메일"),
                                        fieldWithPath("name").type(STRING).description("이름"),
                                        fieldWithPath("phoneNumber")
                                                .type(STRING)
                                                .description("전화번호"),
                                        fieldWithPath("createdAt")
                                                .type(STRING)
                                                .description("생성일시"))))
                .andExpect(status().isOk());
    }

    private BetaReservationResponse createBetaReservationResponse() {
        return BetaReservationResponse.of(
                1L, "compono@test.com", "compono", "010-1234-1234", LocalDateTime.now());
    }
}
