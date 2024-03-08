package com.compono.ibackend.schedule.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.domain.ScheduleTime;
import com.compono.ibackend.schedule.dto.ScheduleWithScheduleTimeDTO;
import com.compono.ibackend.schedule.dto.request.TimeLineRequest;
import com.compono.ibackend.schedule.dto.response.ScheduleTimeResponse;
import com.compono.ibackend.schedule.enumType.TaskStatus;
import com.compono.ibackend.schedule.service.TimeLineService;
import com.compono.ibackend.user.domain.User;
import com.compono.ibackend.user.dto.request.UserAddRequest;
import com.compono.ibackend.user.enumType.OauthProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("[컨드롤러] - 사전예약")
@WebMvcTest(ScheduleTimeController.class)
@AutoConfigureRestDocs
class ScheduleTimeControllerTest {
    private final MockMvc mvc;

    @MockBean private TimeLineService timeLineService;

    ObjectMapper objectMapper;

    ScheduleTimeControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("{GET} 타임라인 조회 - 정상호출")
    @Test
    @WithMockUser(
            username = "ADMIN",
            roles = {"SUPER"})
    void getTimeLine() throws Exception {
        Long userId = 1L;
        LocalDateTime time = LocalDateTime.now();
        int n = 2;
        JSONObject request = new JSONObject();
        request.put("userId", 1L);
        request.put("time", time);
        request.put("n", 2);
        TimeLineRequest timeLineRequest = new TimeLineRequest(userId, time, n);
        List<ScheduleWithScheduleTimeDTO> expectedSchedules = createMockSchedules(); // Mock 데이터 생성

        given(timeLineService.findScheduleTimeWithinRange(userId, time, n))
                .willReturn(expectedSchedules);

        mvc.perform(
                        RestDocumentationRequestBuilders.get("/api/v1/schedule/timeline")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "get-schedule-timeline",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("userId")
                                                .type(NUMBER)
                                                .description("테스트 : 사용자 id (토큰 생기면 없어질 예정)"),
                                        fieldWithPath("time")
                                                .type(STRING)
                                                .description("타임라인 조회할 시"),
                                        fieldWithPath("n")
                                                .type(NUMBER)
                                                .description(
                                                        "테스트 : 임시 시간(시간 체크를 위한 변수입니다. 변수값이 정해지면 없어질 예정)")),
                                responseFields(
                                        fieldWithPath("[].taskName")
                                                .type(STRING)
                                                .description("스케줄 이름"),
                                        fieldWithPath("[].startTime")
                                                .type(STRING)
                                                .description("시작 시간"),
                                        fieldWithPath("[].stopTime")
                                                .type(STRING)
                                                .description("종료 시간"))));
    }

    @DisplayName("{POST} 스케쥴 타임라인 생성 - 시작시간")
    @Test
    @WithMockUser(
            username = "ADMIN",
            roles = {"SUPER"})
    void saveScheduleTimeStartTest() throws Exception {
        Long scheduleId = 1L;
        ScheduleTimeResponse scheduleTime =
                ScheduleTimeResponse.from(createScheduleTime(scheduleId));
        given(timeLineService.createScheduleTimeStart(scheduleId)).willReturn(scheduleTime);

        mvc.perform(
                        RestDocumentationRequestBuilders.post(
                                        "/api/v1/schedule/time/start/{scheduleId}", scheduleId)
                                .with(csrf().asHeader())
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "post-schedule-time-start",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("scheduleId").description("스케쥴 ID")),
                                responseFields(
                                        fieldWithPath("id").type(NUMBER).description("스케줄타임 id"),
                                        fieldWithPath("startTime")
                                                .type(STRING)
                                                .description("시작 시간"),
                                        fieldWithPath("stopTime")
                                                .type(STRING)
                                                .description("종료 시간"))));
    }

    @DisplayName("{POST} 스케쥴 타임라인 업데이트 - 종료시간")
    @Test
    @WithMockUser(
            username = "ADMIN",
            roles = {"SUPER"})
    void updateScheduleTimeStopTest() throws Exception {
        Long scheduleTimeId = 1L;
        ScheduleTimeResponse updatedScheduleTime =
                ScheduleTimeResponse.from(createScheduleTime(scheduleTimeId));
        given(timeLineService.updateScheduleTimeStop(scheduleTimeId))
                .willReturn(updatedScheduleTime);

        mvc.perform(
                        patch("/api/v1/schedule/time/stop/{scheduleTimeId}", scheduleTimeId)
                                .with(csrf().asHeader())
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "patch-schedule-time-stop",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("scheduleTimeId")
                                                .description("스케쥴타임 ID")),
                                responseFields(
                                        fieldWithPath("id").type(NUMBER).description("스케줄타임 id"),
                                        fieldWithPath("startTime")
                                                .type(STRING)
                                                .description("시작 시간"),
                                        fieldWithPath("stopTime")
                                                .type(STRING)
                                                .description("종료 시간"))));
    }

    private List<ScheduleWithScheduleTimeDTO> createMockSchedules() {
        LocalDateTime time = LocalDateTime.now();
        return Arrays.asList(
                new ScheduleWithScheduleTimeDTO("테스트 일정", time, time.plusHours(1)),
                new ScheduleWithScheduleTimeDTO("테스트 일정2", time.plusHours(1), time.plusHours(2)));
    }

    private ScheduleTime createScheduleTime(Long scheduleTimeId) {
        ScheduleTime scheduleTime = new ScheduleTime();
        scheduleTime.setStartTime(LocalDateTime.of(2024, 3, 8, 12, 12));
        scheduleTime.setStopTime(LocalDateTime.of(2024, 3, 8, 13, 12));
        ReflectionTestUtils.setField(scheduleTime, "id", scheduleTimeId);
        return scheduleTime;
    }

    private Schedule createSchedule(Long scheduleId) {
        Schedule schedule =
                Schedule.of(
                        "운동하기",
                        false,
                        LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().plusDays(1).plusHours(1),
                        "헬스장 가기",
                        TaskStatus.IN_PROGRESS,
                        1,
                        false,
                        createUser("test@test.com"));

        ReflectionTestUtils.setField(schedule, "id", scheduleId);

        return schedule;
    }

    private User createUser(String email) {
        UserAddRequest request =
                new UserAddRequest(
                        email, "compono", OauthProvider.KAKAO, UUID.randomUUID().toString(), true);
        return User.from(request);
    }
}
