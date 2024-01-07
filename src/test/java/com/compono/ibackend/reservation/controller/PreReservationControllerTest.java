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
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.compono.ibackend.common.dto.page.request.PageRequest;
import com.compono.ibackend.common.dto.page.request.PageResponse;
import com.compono.ibackend.reservation.domain.PreReservation;
import com.compono.ibackend.reservation.dto.request.PreReservationRequest;
import com.compono.ibackend.reservation.dto.response.PreReservationResponse;
import com.compono.ibackend.reservation.service.PreReservationService;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.RequestDocumentation;
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

    @DisplayName("{GET} 사전예약 페이지 조회 - 정상호출")
    @Test
    @WithMockUser(
            username = "ADMIN",
            roles = {"SUPER"})
    void getAll() throws Exception {
        int page = 0;
        int size = 10;
        String criteria = "createdAt";

        List<PreReservationResponse> content =
                Collections.singletonList(createPreReservationResponse());
        Page<PreReservationResponse> responsePage =
                new PageImpl<>(
                        content,
                        org.springframework.data.domain.PageRequest.of(
                                page, size, Sort.by(Direction.DESC, criteria)),
                        content.size());

        given(preReservationService.findAll(PageRequest.of(page, size, criteria)))
                .willReturn(PageResponse.convertToPageResponse(responsePage));

        mvc.perform(
                        RestDocumentationRequestBuilders.get("/api/v1/pre-reservation")
                                .param("page", String.valueOf(page))
                                .param("size", String.valueOf(size))
                                .param("criteria", criteria)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "get-page-preReservations",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                queryParameters(
                                        RequestDocumentation.parameterWithName("page")
                                                .description("페이지 번호 default(0)"),
                                        RequestDocumentation.parameterWithName("size")
                                                .description("페이지 크기 default(10)"),
                                        RequestDocumentation.parameterWithName("criteria")
                                                .description("정렬 기준 default(createdAt)")),
                                responseFields(
                                        fieldWithPath("content[].id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("사전예약 ID"),
                                        fieldWithPath("content[].email")
                                                .type(STRING)
                                                .description("이메일"),
                                        fieldWithPath("content[].name")
                                                .type(STRING)
                                                .description("이름"),
                                        fieldWithPath("content[].phoneNumber")
                                                .type(STRING)
                                                .description("전화번호"),
                                        fieldWithPath("content[].expectation")
                                                .type(STRING)
                                                .description("바라는 점"),
                                        fieldWithPath("content[].createdAt")
                                                .type(STRING)
                                                .description("사전예약 등록일"),
                                        fieldWithPath("pageNumber")
                                                .type(JsonFieldType.NUMBER)
                                                .description("페이지 수"),
                                        fieldWithPath("pageSize")
                                                .type(JsonFieldType.NUMBER)
                                                .description("페이지 사이즈"),
                                        fieldWithPath("totalPages")
                                                .type(JsonFieldType.NUMBER)
                                                .description("총 페이지 수"),
                                        fieldWithPath("totalElements")
                                                .type(JsonFieldType.NUMBER)
                                                .description("총 요소 수"))));
    }
}
