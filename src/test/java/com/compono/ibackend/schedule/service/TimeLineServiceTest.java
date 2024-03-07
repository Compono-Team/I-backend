package com.compono.ibackend.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.compono.ibackend.common.dto.page.request.PageRequest;
import com.compono.ibackend.common.dto.page.request.PageResponse;
import com.compono.ibackend.reservation.domain.PreReservation;
import com.compono.ibackend.reservation.dto.response.PreReservationResponse;
import com.compono.ibackend.reservation.repository.PreReservationRepository;
import com.compono.ibackend.reservation.service.PreReservationService;
import com.compono.ibackend.schedule.dto.ScheduleWithScheduleTimeDTO;
import com.compono.ibackend.schedule.dto.request.TimeLineRequest;
import com.compono.ibackend.schedule.repository.ScheduleTimeRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
@DisplayName("[비즈니스 로직] - 타임라인")
@ExtendWith(MockitoExtension.class)
class TimeLineServiceTest {

  @InjectMocks
  private TimeLineService timeLineService;

  @Mock
  private ScheduleTimeRepository scheduleTimeRepository;

  @DisplayName("타임라인을 정상적으로 조회한다.")
  @Test
  void givenTimeLineRequest_whenFindScheduleTimeWithinRange_thenScheduleWithTimeDTOs() {
    // Given
    Long userId = 1L;
    LocalDateTime time = LocalDateTime.now();
    int n = 2;

    given(scheduleTimeRepository.findWithinTimeRangeByUserId(userId, time.minusHours(n), time.plusHours(n)))
        .willReturn(Arrays.asList(
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
        ));

    // When
    List<ScheduleWithScheduleTimeDTO> result = timeLineService.findScheduleTimeWithinRange(userId, time, n);

    // Then
    assertThat(result).hasSize(2);
    assertThat(result.get(0)).isEqualTo(
        new ScheduleWithScheduleTimeDTO(
            "테스트 일정",
            time,
            time.plusHours(1)
        )
    );
    assertThat(result.get(1)).isEqualTo(
        new ScheduleWithScheduleTimeDTO(
            "테스트 일정2",
            time.plusHours(1),
            time.plusHours(2)
        )
    );
  }
}