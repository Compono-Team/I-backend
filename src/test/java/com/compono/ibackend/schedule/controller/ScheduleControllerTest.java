package com.compono.ibackend.schedule.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
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

import com.compono.ibackend.common.security.impl.UserDetailsImpl;
import com.compono.ibackend.common.utils.jwt.JwtProvider;
import com.compono.ibackend.factory.ScheduleFactory;
import com.compono.ibackend.factory.TagFactory;
import com.compono.ibackend.factory.UserFactory;
import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.dto.request.SchedulePointRequest;
import com.compono.ibackend.schedule.dto.request.ScheduleRequest;
import com.compono.ibackend.schedule.dto.response.ScheduleResponse;
import com.compono.ibackend.schedule.service.ScheduleService;
import com.compono.ibackend.tag.domain.Tag;
import com.compono.ibackend.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("[컨트롤러] - 스케줄")
@WebMvcTest(ScheduleController.class)
@AutoConfigureRestDocs
class ScheduleControllerTest {

    private static final String EMAIL = "compono@test.com";
    private final MockMvc mvc;
    private final ObjectMapper objectMapper;
    @MockBean private ScheduleService scheduleService;
    @MockBean private JwtProvider jwtProvider;

    ScheduleControllerTest(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @DisplayName("{POST} 스케줄 생성 - 정상호출")
    @Test
    @WithMockUser(
            username = "ADMIN",
            roles = {"SUPER"})
    void addSchedule() throws Exception {
        User user = UserFactory.createUser(EMAIL);
        ReflectionTestUtils.setField(user, "id", 1L);
        UserDetailsImpl userDetails = new UserDetailsImpl((user));
        String accessToken = jwtProvider.createAccessToken(user.getEmail());

        Tag tag1 = TagFactory.createTag("공부");
        Tag tag2 = TagFactory.createTag("자기계발");
        ReflectionTestUtils.setField(tag1, "id", 1L);
        ReflectionTestUtils.setField(tag2, "id", 2L);

        Schedule schedule = ScheduleFactory.createSchedule(user, List.of(tag1, tag2));
        ScheduleRequest scheduleRequest =
                new ScheduleRequest(
                        schedule.getTaskName(),
                        schedule.getPriority(),
                        List.of(tag1.getId(), tag2.getId()),
                        schedule.getStartDate(),
                        schedule.getEndDate(),
                        new SchedulePointRequest(
                                schedule.getPoint().getLongitude(),
                                schedule.getPoint().getLatitude()),
                        schedule.getIsRoutine(),
                        schedule.getRoutinePeriod(),
                        schedule.getIsMarked());
        ScheduleResponse scheduleResponse = ScheduleResponse.from(schedule);

        given(scheduleService.addSchedule(EMAIL, scheduleRequest)).willReturn(scheduleResponse);

        mvc.perform(
                        RestDocumentationRequestBuilders.post("/api/v1/schedule")
                                .with(csrf().asHeader())
                                .header(HttpHeaders.AUTHORIZATION, UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(scheduleRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "post-schedule",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION)
                                                .description("Bearer {access-token}")),
                                requestFields(
                                        fieldWithPath("taskName").type(STRING).description("테스크 명"),
                                        fieldWithPath("priority")
                                                .type(STRING)
                                                .description("테스크 우선순위")
                                                .attributes(new Attributes.Attribute("format", "")),
                                        fieldWithPath("tags")
                                                .type(JsonFieldType.ARRAY)
                                                .description("테스크가 등록될 Tag id 리스트"),
                                        fieldWithPath("startDate")
                                                .type(STRING)
                                                .description("테스크 시간 시간"),
                                        fieldWithPath("endDate")
                                                .type(STRING)
                                                .description("테스크 종료 시간"),
                                        fieldWithPath("point.longitude")
                                                .type(STRING)
                                                .description("테스크 수행 위치 (경도)"),
                                        fieldWithPath("point.latitude")
                                                .type(STRING)
                                                .description("테스크 수행 위치 (위도)"),
                                        fieldWithPath("isRoutine")
                                                .type(BOOLEAN)
                                                .description("반복되는 일정 여부"),
                                        fieldWithPath("routinePeriod")
                                                .type(STRING)
                                                .description("테스크 우선순위"),
                                        fieldWithPath("isMarked")
                                                .type(BOOLEAN)
                                                .description("캘린더에 표시 여부")),
                                responseFields(
                                        fieldWithPath("id").type(NUMBER).description("테스크 id"),
                                        fieldWithPath("taskName")
                                                .type(STRING)
                                                .description("테스크 명"))));
    }
}
