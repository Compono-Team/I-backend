package com.compono.ibackend.schedule.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.compono.ibackend.common.dto.page.request.PageRequest;
import com.compono.ibackend.common.dto.page.request.PageResponse;
import com.compono.ibackend.reservation.controller.PreReservationController;
import com.compono.ibackend.reservation.dto.response.PreReservationResponse;
import com.compono.ibackend.reservation.service.PreReservationService;
import com.compono.ibackend.schedule.dto.ScheduleWithScheduleTimeDTO;
import com.compono.ibackend.schedule.dto.request.TimeLineRequest;
import com.compono.ibackend.schedule.service.TimeLineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
class ScheduleTimeControllerTest {
  private final MockMvc mvc;

  @MockBean
  private TimeLineService timeLineService;

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

    TimeLineRequest timeLineRequest = new TimeLineRequest(userId, time, n);
    List<ScheduleWithScheduleTimeDTO> expectedSchedules = createMockSchedules(); // Mock 데이터 생성


    given(timeLineService.findScheduleTimeWithinRange(userId,time,n))
        .willReturn(expectedSchedules);

    mvc.perform(
            RestDocumentationRequestBuilders.get("/api/v1/schedule/timeline")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(timeLineRequest)))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(
            document(
                "get-schedule-timeline",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                queryParameters(
                    RequestDocumentation.parameterWithName("userId")
                        .description("테스트 : 사용자 id (토큰 생기면 없어질 예정)"),
                    RequestDocumentation.parameterWithName("time")
                        .description("타임라인 조회할 시"),
                    RequestDocumentation.parameterWithName("n")
                        .description("테스트 : 임시 시간(시간 체크를 위한 변수입니다. 변수값이 정해지면 없어질 예정)")),
                responseFields(
                    fieldWithPath("content[].taskName")
                        .type(STRING)
                        .description("스케줄 이름"),
                    fieldWithPath("content[].name")
                        .type(STRING)
                        .description("이름"),
                    fieldWithPath("content[].startTime")
                        .type(STRING)
                        .description("시작 시간"),
                    fieldWithPath("content[].stopTime")
                        .type(STRING)
                        .description("끝난 시간")
                    )));
  }

  private List<ScheduleWithScheduleTimeDTO> createMockSchedules() {
    LocalDateTime time = LocalDateTime.now();
    return Arrays.asList(
        new ScheduleWithScheduleTimeDTO(
            "테스트 일정",
            time,
            time.plusHours(1)
        ),
        new ScheduleWithScheduleTimeDTO(
            "테스트 일정2",
            time.plusHours(1),
            time.plusHours(2)
        )
    );
  }

}