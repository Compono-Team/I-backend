package com.compono.ibackend.schedule.controller;

import static com.compono.ibackend.common.fixtures.ScheduleTimeFixtures.YEAR_2024_MONTH_04_DAY_17_HOUR_12_AND_13_SCHEDULE_TIME;
import static com.compono.ibackend.common.fixtures.ScheduleTimeFixtures.YEAR_2024_MONTH_04_DAY_17_HOUR_12_SCHEDULE_TIME;
import static com.compono.ibackend.common.fixtures.ScheduleTimeFixtures.YEAR_2024_MONTH_04_DAY_17_HOUR_14_AND_17_SCHEDULE_TIME;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.compono.ibackend.common.ApiDocsTest;
import com.compono.ibackend.common.fixtures.ScheduleFixtures;
import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.domain.ScheduleTime;
import com.compono.ibackend.schedule.dto.SchedulesWithTimesDTO;
import com.compono.ibackend.schedule.dto.response.ScheduleTimeResponse;
import com.compono.ibackend.schedule.service.ScheduleTimeService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

@DisplayName("[컨드롤러] - 타임라인")
@WebMvcTest(ScheduleTimeController.class)
class ScheduleTimeControllerTest extends ApiDocsTest {

    @MockBean private ScheduleTimeService scheduleTimeService;

    @DisplayName("{GET} 타임라인 조회 - 정상호출")
    @Test
    @WithMockUser(
            username = "ADMIN",
            roles = {"SUPER"})
    void getDailySchedules() throws Exception {

        Schedule schedule1 = Mockito.spy(ScheduleFixtures.YEAR_2024_MONTH_04_DAY_17_SCHEDULE(1L));
        Schedule schedule2 =
                Mockito.spy(ScheduleFixtures.YEAR_2024_MONTH_04_DAY_17_AND_18_SCHEDULE(1L));
        List<ScheduleTime> scheduleTime1 =
                List.of(
                        Mockito.spy(
                                YEAR_2024_MONTH_04_DAY_17_HOUR_14_AND_17_SCHEDULE_TIME(schedule1)));
        List<ScheduleTime> scheduleTime2 =
                List.of(
                        Mockito.spy(
                                YEAR_2024_MONTH_04_DAY_17_HOUR_12_AND_13_SCHEDULE_TIME(schedule2)));
        when(schedule1.getId()).thenReturn(1L);
        when(schedule2.getId()).thenReturn(2L);
        when(scheduleTime1.get(0).getId()).thenReturn(1L);
        when(scheduleTime2.get(0).getId()).thenReturn(1L);
        when(schedule1.getScheduleTimes()).thenReturn(scheduleTime1);
        when(schedule2.getScheduleTimes()).thenReturn(scheduleTime2);

        List<Schedule> schedules = List.of(schedule1, schedule2);
        SchedulesWithTimesDTO response = SchedulesWithTimesDTO.from(schedules);
        given(
                        scheduleTimeService.findSchedulesAndTimeInPeriod(
                                anyLong(), anyInt(), anyInt(), anyInt()))
                .willReturn(response);

        mvc.perform(
                        RestDocumentationRequestBuilders.get("/api/v1/schedule/timeline")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", String.valueOf(1L))
                                .param("year", "2024")
                                .param("month", "4")
                                .param("day", "17"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "timeline/schedules/getDailySchedules",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                queryParameters(
                                        // todo
                                        parameterWithName("userId")
                                                .description("임시 테스트 용 userId 추후 토큰으로 변경 예정"),
                                        parameterWithName("year").description("조회할 연도"),
                                        parameterWithName("month").description("조회할 월"),
                                        parameterWithName("day").optional().description("조회할 일")),
                                responseFields(
                                        fieldWithPath("scheduleWithTimes")
                                                .type(JsonFieldType.ARRAY)
                                                .description("데일리 일정 리스트"),
                                        fieldWithPath("scheduleWithTimes[0].scheduleId")
                                                .type(NUMBER)
                                                .description("스케줄 ID"),
                                        fieldWithPath("scheduleWithTimes[0].taskName")
                                                .type(STRING)
                                                .description("스케줄 이름"),
                                        fieldWithPath("scheduleWithTimes[0].scheduleTimeList")
                                                .type(JsonFieldType.ARRAY)
                                                .description("일정 타임라인 리스트"),
                                        fieldWithPath("scheduleWithTimes[0].scheduleTimeList[0].id")
                                                .type(NUMBER)
                                                .description("타임라인 ID"),
                                        fieldWithPath(
                                                        "scheduleWithTimes[0].scheduleTimeList[0].stopTime")
                                                .type(STRING)
                                                .description("종료 시간"),
                                        fieldWithPath(
                                                        "scheduleWithTimes[0].scheduleTimeList[0].startTime")
                                                .type(STRING)
                                                .description("시작 시간"))));
    }

    @DisplayName("{POST} 스케쥴 타임라인 생성 - 시작시간")
    @Test
    @WithMockUser(
            username = "ADMIN",
            roles = {"SUPER"})
    void saveScheduleTimeStartTest() throws Exception {

        Long scheduleId = 1L;
        Schedule schedule = Mockito.spy(ScheduleFixtures.YEAR_2024_MONTH_04_DAY_17_SCHEDULE(1L));

        ScheduleTime scheduleTime =
                Mockito.spy(YEAR_2024_MONTH_04_DAY_17_HOUR_12_SCHEDULE_TIME(schedule));
        when(schedule.getId()).thenReturn(scheduleId);
        when(scheduleTime.getId()).thenReturn(1L);
        ScheduleTimeResponse response = ScheduleTimeResponse.from(scheduleTime);
        given(scheduleTimeService.create(anyLong(), anyString())).willReturn(response);

        mvc.perform(
                        RestDocumentationRequestBuilders.post(
                                        "/api/v1/schedule/time/start/{scheduleId}", scheduleId)
                                .with(csrf().asHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("startDateString", "202404171200"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "timeline/scheduleTime/start",
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
                                                .description("종료 시간")
                                                .optional())));
    }

    @DisplayName("{PATCH} 스케쥴 타임라인 업데이트 - 종료시간")
    @Test
    @WithMockUser(
            username = "ADMIN",
            roles = {"SUPER"})
    void updateScheduleTimeStopTest() throws Exception {
        Long scheduleTimeId = 1L;
        Schedule schedule = Mockito.spy(ScheduleFixtures.YEAR_2024_MONTH_04_DAY_17_SCHEDULE(1L));

        ScheduleTime scheduleTime =
                Mockito.spy(YEAR_2024_MONTH_04_DAY_17_HOUR_14_AND_17_SCHEDULE_TIME(schedule));

        when(scheduleTime.getId()).thenReturn(1L);
        ScheduleTimeResponse response = ScheduleTimeResponse.from(scheduleTime);
        given(scheduleTimeService.update(anyLong(), anyString())).willReturn(response);

        mvc.perform(
                        patch("/api/v1/schedule/time/stop/{scheduleTimeId}", scheduleTimeId)
                                .with(csrf().asHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("stopDateString", "202404171700"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "timeline/scheduleTime/stop",
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
}
