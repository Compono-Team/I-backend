package com.compono.ibackend.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.domain.ScheduleTime;
import com.compono.ibackend.schedule.dto.ScheduleWithScheduleTimeDTO;
import com.compono.ibackend.schedule.dto.response.ScheduleTimeResponse;
import com.compono.ibackend.schedule.enumType.TaskStatus;
import com.compono.ibackend.schedule.repository.ScheduleRepository;
import com.compono.ibackend.schedule.repository.ScheduleTimeRepository;
import com.compono.ibackend.user.domain.User;
import com.compono.ibackend.user.dto.request.UserAddRequest;
import com.compono.ibackend.user.enumType.OauthProvider;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("[비즈니스 로직] - 타임라인")
@ExtendWith(MockitoExtension.class)
class TimeLineServiceTest {

    @InjectMocks private TimeLineService timeLineService;
    @Mock private ScheduleRepository scheduleRepository;
    @Mock private ScheduleTimeRepository scheduleTimeRepository;

    @DisplayName("스케쥴 시작 시간을 생성한다.")
    @Test
    void givenScheduleId_whenCreateScheduleTimeStart_thenScheduleTime() {
        Long id = 1L;
        Schedule schedule = createSchedule(id);
        given(scheduleRepository.findById(id)).willReturn(Optional.of(schedule));
        given(scheduleTimeRepository.save(any(ScheduleTime.class)))
                .willReturn(createScheduleTime(id));
        ScheduleTimeResponse result = timeLineService.createScheduleTimeStart(id);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.startTime()).isEqualTo(LocalDateTime.of(2024, 3, 8, 12, 12));
    }

    @DisplayName("스케쥴 종료 시간을 업데이트한다.")
    @Test
    void givenScheduleTimeId_whenUpdateScheduleTimeStop_thenScheduleTime() {
        Long id = 1L;
        ScheduleTime scheduleTime = createScheduleTime(id);
        given(scheduleTimeRepository.findById(id)).willReturn(Optional.of(scheduleTime));
        given(scheduleTimeRepository.save(any(ScheduleTime.class))).willReturn(scheduleTime);
        ScheduleTimeResponse result = timeLineService.updateScheduleTimeStop(id);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.startTime()).isEqualTo(LocalDateTime.of(2024, 3, 8, 12, 12));
    }

    @DisplayName("타임라인을 정상적으로 조회한다.")
    @Test
    void givenTimeLineRequest_whenFindScheduleTimeWithinRange_thenScheduleWithTimeDTOs() {
        // Given
        Long userId = 1L;
        LocalDateTime time = LocalDateTime.now();
        int n = 2;

        given(
                        scheduleTimeRepository.findWithinTimeRangeByUserId(
                                userId, time.minusHours(n), time.plusHours(n)))
                .willReturn(
                        Arrays.asList(
                                new ScheduleWithScheduleTimeDTO("테스트 일정", time, time.plusHours(1)),
                                new ScheduleWithScheduleTimeDTO(
                                        "테스트 일정2", time.plusHours(1), time.plusHours(2))));

        // When
        List<ScheduleWithScheduleTimeDTO> result =
                timeLineService.findScheduleTimeWithinRange(userId, time, n);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0))
                .isEqualTo(new ScheduleWithScheduleTimeDTO("테스트 일정", time, time.plusHours(1)));
        assertThat(result.get(1))
                .isEqualTo(
                        new ScheduleWithScheduleTimeDTO(
                                "테스트 일정2", time.plusHours(1), time.plusHours(2)));
    }

    private ScheduleTime createScheduleTime(Long scheduleTimeId) {
        ScheduleTime scheduleTime = new ScheduleTime();
        scheduleTime.setStartTime(LocalDateTime.of(2024, 3, 8, 12, 12));
        ReflectionTestUtils.setField(scheduleTime, "id", scheduleTimeId);
        return scheduleTime;
    }

    private User createUser(String email) {
        UserAddRequest request =
                new UserAddRequest(
                        email, "compono", OauthProvider.KAKAO, UUID.randomUUID().toString(), true);
        return User.from(request);
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
}
