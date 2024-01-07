package com.compono.ibackend.reservation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.compono.ibackend.reservation.domain.PreReservation;
import com.compono.ibackend.reservation.dto.request.PreReservationRequest;
import com.compono.ibackend.reservation.dto.response.PreReservationResponse;
import com.compono.ibackend.reservation.service.PreReservationService;
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

@DisplayName("[컨드롤러] - 사전예약")
@WebMvcTest(PreReservationController.class)
@AutoConfigureRestDocs
class PreReservationControllerTest {

    private final MockMvc mvc;

    @MockBean private PreReservationService preReservationService;

    PreReservationControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("{POST} 사전예약 등록 - 정상호출")
    @Test
    @WithMockUser(
            username = "ADMIN",
            roles = {"SUPER"})
    void addPreReservation() throws Exception {
        JSONObject request = new JSONObject();
        request.put("email", "compono@test.com");
        request.put("name", "compono");
        request.put("phoneNumber", "010-1234-1234");
        request.put("expectation", "바라는 점은..");

        when(preReservationService.addPreReservation(any(PreReservationRequest.class)))
                .thenReturn(createPreReservationResponse());

        mvc.perform(
                        RestDocumentationRequestBuilders.post("/api/v1/pre-reservation")
                                .with(csrf().asHeader())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(request.toString()))
                .andDo(print())
                .andDo(
                        document(
                                "create-preReservation",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("email").type(STRING).description("이메일"),
                                        fieldWithPath("name").type(STRING).description("이름"),
                                        fieldWithPath("phoneNumber")
                                                .type(STRING)
                                                .description("전화번호"),
                                        fieldWithPath("expectation")
                                                .type(STRING)
                                                .description("바라는 점")),
                                responseFields(
                                        fieldWithPath("id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("생성된 사전예약 id"),
                                        fieldWithPath("email").type(STRING).description("이메일"),
                                        fieldWithPath("name").type(STRING).description("이름"),
                                        fieldWithPath("phoneNumber")
                                                .type(STRING)
                                                .description("전화번호"),
                                        fieldWithPath("expectation")
                                                .type(STRING)
                                                .description("바라는 점"),
                                        fieldWithPath("createdAt")
                                                .type(STRING)
                                                .description("생성일시"))))
                .andExpect(status().isOk());
    }

    private PreReservationResponse createPreReservationResponse() {
        return PreReservationResponse.of(
                1L,
                "compono@test.com",
                "compono",
                "010-1234-1234",
                "바라는 점은..",
                LocalDateTime.now());
    }

    private PreReservation createPreReservation() {
        return PreReservation.of("compono@test.com", "compono", "010-1234-1234", "바라는 점은..");
    }

    @DisplayName("{GET} 사전예약 단건조회 - 정상호출")
    @Test
    @WithMockUser(
            username = "ADMIN",
            roles = {"SUPER"})
    void getPreReservation() throws Exception {
        Long preReservationId = 1L;
        PreReservationResponse preReservationResponse = createPreReservationResponse();

        given(preReservationService.findPreReservation(preReservationId))
                .willReturn(preReservationResponse);

        mvc.perform(
                        RestDocumentationRequestBuilders.get(
                                "/api/v1/pre-reservation/{preReservationId}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "get-preReservation",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("preReservationId")
                                                .description("사전예약 ID")),
                                responseFields(
                                        fieldWithPath("id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("사전예약 ID"),
                                        fieldWithPath("email").type(STRING).description("이메일"),
                                        fieldWithPath("name").type(STRING).description("이름"),
                                        fieldWithPath("phoneNumber")
                                                .type(STRING)
                                                .description("전화번호"),
                                        fieldWithPath("expectation")
                                                .type(STRING)
                                                .description("바라는 점"),
                                        fieldWithPath("createdAt")
                                                .type(STRING)
                                                .description("사전예약 등록일"))));
    }
}
