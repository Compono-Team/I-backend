package com.compono.ibackend.schedule.controller;

import static org.mockito.Mockito.when;
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

import com.compono.ibackend.annotation.WithUserPrincipals;
import com.compono.ibackend.factory.ScheduleFactory;
import com.compono.ibackend.factory.TagFactory;
import com.compono.ibackend.factory.UserFactory;
import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.dto.request.SchedulePointRequest;
import com.compono.ibackend.schedule.dto.request.ScheduleRequest;
import com.compono.ibackend.schedule.dto.response.ScheduleDetailResponse;
import com.compono.ibackend.schedule.dto.response.ScheduleDetailWithTagResponse;
import com.compono.ibackend.schedule.dto.response.ScheduleResponse;
import com.compono.ibackend.schedule.dto.response.TagDetailResponse;
import com.compono.ibackend.schedule.enumType.RoutinePeriod;
import com.compono.ibackend.schedule.enumType.SchedulePriority;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("[컨트롤러] - 스케줄")
@WebMvcTest(ScheduleController.class)
@AutoConfigureRestDocs
class ScheduleControllerTest {

    private static final String EMAIL = "compono@test.com";
    private static MockMvc mvc;
    private static ObjectMapper objectMapper;
    @MockBean private ScheduleService scheduleService;

    ScheduleControllerTest(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @DisplayName("{POST} 스케줄 생성 - 정상호출")
    @Test
    @WithUserPrincipals
    void addSchedule() throws Exception {
        List<Tag> tags = createTags();
        Schedule schedule = createSchedule(tags);
        ScheduleRequest scheduleRequest = createScheduleRequest(schedule, tags);
        ScheduleResponse scheduleResponse = ScheduleResponse.from(schedule);

        when(scheduleService.addSchedule(EMAIL, scheduleRequest)).thenReturn(scheduleResponse);

        mvc.perform(
                        RestDocumentationRequestBuilders.post("/api/v1/schedule")
                                .with(csrf().asHeader())
                                .header(HttpHeaders.AUTHORIZATION, UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
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
                                                .attributes(
                                                        new Attributes.Attribute(
                                                                "format",
                                                                SchedulePriority.getAllType())),
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
                                                .type(NUMBER)
                                                .description("테스크 수행 위치 (경도)"),
                                        fieldWithPath("point.latitude")
                                                .type(NUMBER)
                                                .description("테스크 수행 위치 (위도)"),
                                        fieldWithPath("isRoutine")
                                                .type(BOOLEAN)
                                                .description("반복되는 일정 여부"),
                                        fieldWithPath("routinePeriod")
                                                .type(STRING)
                                                .description("테스크 우선순위")
                                                .attributes(
                                                        new Attributes.Attribute(
                                                                "format",
                                                                RoutinePeriod.getAllType()))
                                                .attributes(
                                                        new Attributes.Attribute(
                                                                "기타", "isRoutine 인 경우, NONE만 가능")),
                                        fieldWithPath("isMarked")
                                                .type(BOOLEAN)
                                                .description("캘린더에 표시 여부")),
                                responseFields(
                                        fieldWithPath("id").type(NUMBER).description("테스크 id"),
                                        fieldWithPath("taskName")
                                                .type(STRING)
                                                .description("테스크 명"))));
    }

    @DisplayName("{DELETE} 스케줄 단일 삭제 - 정상호출")
    @Test
    @WithUserPrincipals
    void deleteSchedule() throws Exception {
        List<Tag> tags = createTags();
        Schedule schedule = createSchedule(tags);

        when(scheduleService.deleteSchedule(EMAIL, 1L)).thenReturn(true);

        mvc.perform(
                        RestDocumentationRequestBuilders.delete("/api/v1/schedule/{scheduleId}", 1)
                                .with(csrf().asHeader())
                                .header(HttpHeaders.AUTHORIZATION, UUID.randomUUID().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "delete-schedule",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION)
                                                .description("Bearer {access-token}"))));
    }

    @DisplayName("{GET} 스케줄 상세 조회 - 정상호출")
    @Test
    @WithUserPrincipals
    void getSchedule() throws Exception {
        List<Tag> tags = createTags();
        Schedule schedule = createSchedule(tags);
        ScheduleDetailWithTagResponse scheduleDetailResponse =
                createScheduleDetailWithTagResponse(schedule, tags);

        when(scheduleService.findScheduleDetailById(EMAIL, 1L)).thenReturn(scheduleDetailResponse);

        mvc.perform(
                        RestDocumentationRequestBuilders.get("/api/v1/schedule/{scheduleId}", 1)
                                .with(csrf().asHeader())
                                .header(HttpHeaders.AUTHORIZATION, UUID.randomUUID().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "get-schedule",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION)
                                                .description("Bearer {access-token}")),
                                responseFields(
                                        fieldWithPath("schedule.id")
                                                .type(NUMBER)
                                                .description("테스크 id"),
                                        fieldWithPath("schedule.taskName")
                                                .type(STRING)
                                                .description("테스크 명"),
                                        fieldWithPath("schedule.priority")
                                                .type(STRING)
                                                .description("테스크 우선순위")
                                                .attributes(
                                                        new Attributes.Attribute(
                                                                "format",
                                                                SchedulePriority.getAllType())),
                                        fieldWithPath("schedule.startDate")
                                                .type(STRING)
                                                .description("테스크 시간 시간"),
                                        fieldWithPath("schedule.endDate")
                                                .type(STRING)
                                                .description("테스크 종료 시간"),
                                        fieldWithPath("schedule.point.longitude")
                                                .type(NUMBER)
                                                .description("테스크 수행 위치 (경도)"),
                                        fieldWithPath("schedule.point.latitude")
                                                .type(NUMBER)
                                                .description("테스크 수행 위치 (위도)"),
                                        fieldWithPath("schedule.isRoutine")
                                                .type(BOOLEAN)
                                                .description("반복되는 일정 여부"),
                                        fieldWithPath("schedule.routinePeriod")
                                                .type(STRING)
                                                .description("테스크 우선순위")
                                                .attributes(
                                                        new Attributes.Attribute(
                                                                "format",
                                                                RoutinePeriod.getAllType())),
                                        fieldWithPath("schedule.isMarked")
                                                .type(BOOLEAN)
                                                .description("캘린더에 표시 여부"),
                                        fieldWithPath("tags[].tagId")
                                                .type(NUMBER)
                                                .description("태그 id"),
                                        fieldWithPath("tags[].name")
                                                .type(STRING)
                                                .description("태그 명"),
                                        fieldWithPath("tags[].color")
                                                .type(STRING)
                                                .description("태그 컬러"))));
    }

    public List<Tag> createTags() {
        Tag tag1 = TagFactory.createTag("공부");
        Tag tag2 = TagFactory.createTag("자기계발");
        ReflectionTestUtils.setField(tag1, "id", 1L);
        ReflectionTestUtils.setField(tag2, "id", 2L);

        return List.of(tag1, tag2);
    }

    public Schedule createSchedule(List<Tag> tags) {
        User user = UserFactory.createUser(EMAIL);
        ReflectionTestUtils.setField(user, "id", 1L);

        return ScheduleFactory.createSchedule(user.getId(), tags);
    }

    public ScheduleRequest createScheduleRequest(Schedule schedule, List<Tag> tags) {
        return new ScheduleRequest(
                schedule.getTaskName(),
                schedule.getPriority(),
                tags.stream().map(tag -> tag.getId()).toList(),
                schedule.getStartDate(),
                schedule.getEndDate(),
                new SchedulePointRequest(
                        schedule.getPoint().getLongitude(), schedule.getPoint().getLatitude()),
                schedule.getIsRoutine(),
                schedule.getRoutinePeriod(),
                schedule.getIsMarked());
    }

    public ScheduleDetailWithTagResponse createScheduleDetailWithTagResponse(
            Schedule schedule, List<Tag> tags) {
        ScheduleDetailResponse scheduleDetailResponse = ScheduleDetailResponse.of(schedule);

        List<TagDetailResponse> tagDetailResponses =
                tags.stream()
                        .map(
                                tag ->
                                        new TagDetailResponse(
                                                tag.getId(), tag.getName(), tag.getColor()))
                        .toList();
        return new ScheduleDetailWithTagResponse(scheduleDetailResponse, tagDetailResponses);
    }
}
