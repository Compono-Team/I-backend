package com.compono.ibackend.schedule.service;

import static com.compono.ibackend.common.enumType.ErrorCode.NOT_FOUND_SCHEDULE;
import static com.compono.ibackend.common.enumType.ErrorCode.NOT_FOUND_SCHEDULE_TIME;
import static com.compono.ibackend.common.enumType.ErrorCode.SCHEDULE_END_TIME_BEFORE_START_TIME;
import static com.compono.ibackend.common.fixtures.ScheduleFixtures.YEAR_2024_MONTH_04_DAY_17_AND_18_SCHEDULE;
import static com.compono.ibackend.common.fixtures.ScheduleFixtures.YEAR_2024_MONTH_04_DAY_17_SCHEDULE;
import static com.compono.ibackend.common.fixtures.ScheduleTimeFixtures.YEAR_2024_MONTH_04_DAY_17_HOUR_12_SCHEDULE_TIME;
import static com.compono.ibackend.common.fixtures.UserFixtures.COMPONO_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.compono.ibackend.common.ServiceTest;
import com.compono.ibackend.common.exception.BadRequestException;
import com.compono.ibackend.oauth.service.GoogleOauthService;
import com.compono.ibackend.oauth.service.KakaoOauthService;
import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.domain.ScheduleTime;
import com.compono.ibackend.schedule.dto.ScheduleWithTimesDTO;
import com.compono.ibackend.schedule.dto.SchedulesWithTimesDTO;
import com.compono.ibackend.schedule.dto.response.ScheduleTimeResponse;
import com.compono.ibackend.schedule.repository.ScheduleRepository;
import com.compono.ibackend.schedule.repository.ScheduleTimeRepository;
import com.compono.ibackend.user.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayName("[비즈니스 로직] - 타임라인")
class ScheduleTimeServiceTest extends ServiceTest {
    // todo 밑 오류로 인한 임시조치 -> 삭제 필요
    // Parameter 0 of constructor in com.compono.ibackend.oauth.service.KakaoOauthService required a
    // bean of type

    @MockBean private KakaoOauthService kakaoOauthService;
    @MockBean private GoogleOauthService googleOauthService;
    @Autowired private ScheduleTimeService scheduleTimeService;
    @Autowired private ScheduleRepository scheduleRepository;
    @Autowired private ScheduleTimeRepository scheduleTimeRepository;

    @Nested
    @DisplayName("스케쥴 시간을 생성시")
    class createScheduleTime {

        @DisplayName("[정상 케이스] 성공적으로 생성한다.")
        @Test
        void success() {
            // Given
            Long id = 1L;
            List<Schedule> schedule =
                    testFixtureBuilder.buildSchedule(
                            List.of(YEAR_2024_MONTH_04_DAY_17_SCHEDULE(id)));
            String YEAR_2024_MONTH_04_DAY_17_HOUR_12_MIN_00_STRING = "202404171200";
            LocalDateTime YEAR_2024_MONTH_04_DAY_17_HOUR_12_MIN_00_DATE_ =
                    LocalDateTime.of(2024, 04, 17, 12, 00);
            // When
            ScheduleTimeResponse result =
                    scheduleTimeService.create(
                            schedule.get(0).getId(),
                            YEAR_2024_MONTH_04_DAY_17_HOUR_12_MIN_00_STRING);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.startTime())
                    .isEqualTo(YEAR_2024_MONTH_04_DAY_17_HOUR_12_MIN_00_DATE_);
        }

        @DisplayName("[오류 케이스 - NOT_FOUND_SCHEDULE] 스케줄이 존재하지 않으면 예외가 발생한다.")
        @Test
        void failScheduleNotExistById() {
            // given
            User user = testFixtureBuilder.buildUser(COMPONO_USER());
            Long userId = user.getId();
            List<Schedule> schedule =
                    testFixtureBuilder.buildSchedule(
                            List.of(YEAR_2024_MONTH_04_DAY_17_SCHEDULE(userId)));

            String YEAR_2024_MONTH_04_DAY_17_HOUR_13_MIN_00_STRING = "202404171300";
            Long notExistScheduleId = -1L;

            // when & then
            assertThatThrownBy(
                            () ->
                                    scheduleTimeService.create(
                                            notExistScheduleId,
                                            YEAR_2024_MONTH_04_DAY_17_HOUR_13_MIN_00_STRING))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessageContaining(NOT_FOUND_SCHEDULE.getMsg());
        }
    }

    @Nested
    @DisplayName("스케쥴 시간을 수정시")
    class updateScheduleTime {

        @DisplayName("[정상 케이스] 성공적으로 수정한다.")
        @Test
        void success() {
            // Given
            User user = testFixtureBuilder.buildUser(COMPONO_USER());
            Long userId = user.getId();
            List<Schedule> schedule =
                    testFixtureBuilder.buildSchedule(
                            List.of(YEAR_2024_MONTH_04_DAY_17_SCHEDULE(userId)));
            ScheduleTime scheduleTime =
                    testFixtureBuilder.buildScheduleTime(
                            YEAR_2024_MONTH_04_DAY_17_HOUR_12_SCHEDULE_TIME(schedule.get(0)));
            String YEAR_2024_MONTH_04_DAY_17_HOUR_13_MIN_00_STRING = "202404171300";
            LocalDateTime YEAR_2024_MONTH_04_DAY_17_HOUR_13_MIN_00_DATE_ =
                    LocalDateTime.of(2024, 04, 17, 13, 00);
            // When
            ScheduleTimeResponse result =
                    scheduleTimeService.update(
                            scheduleTime.getId(), YEAR_2024_MONTH_04_DAY_17_HOUR_13_MIN_00_STRING);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.id()).isEqualTo(scheduleTime.getId());
            assertThat(result.stopTime()).isEqualTo(YEAR_2024_MONTH_04_DAY_17_HOUR_13_MIN_00_DATE_);
        }

        @DisplayName("[오류 케이스 - NOT_FOUND_SCHEDULE_TIME] 스케줄 타임이 존재하지 않으면 예외가 발생한다.")
        @Test
        void failScheduleTimeNotExistById() {
            // given
            User user = testFixtureBuilder.buildUser(COMPONO_USER());
            Long userId = user.getId();
            List<Schedule> schedule =
                    testFixtureBuilder.buildSchedule(
                            List.of(YEAR_2024_MONTH_04_DAY_17_SCHEDULE(userId)));
            ScheduleTime scheduleTime =
                    testFixtureBuilder.buildScheduleTime(
                            YEAR_2024_MONTH_04_DAY_17_HOUR_12_SCHEDULE_TIME(schedule.get(0)));
            String YEAR_2024_MONTH_04_DAY_17_HOUR_13_MIN_00_STRING = "202404171300";
            Long notExistScheduleTimeId = -1L;

            // when & then
            assertThatThrownBy(
                            () ->
                                    scheduleTimeService.update(
                                            notExistScheduleTimeId,
                                            YEAR_2024_MONTH_04_DAY_17_HOUR_13_MIN_00_STRING))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessageContaining(NOT_FOUND_SCHEDULE_TIME.getMsg());
        }

        @DisplayName("[오류 케이스 - SCHEDULE_END_TIME_BEFORE_START_TIME] 종료 시간이 시작 시간보다 이르면 예외가 발생한다.")
        @Test
        void failWhenEndTimeBeforeStartTime() {
            // Given
            User user = testFixtureBuilder.buildUser(COMPONO_USER());
            Long userId = user.getId();
            List<Schedule> schedule =
                    testFixtureBuilder.buildSchedule(
                            List.of(YEAR_2024_MONTH_04_DAY_17_SCHEDULE(userId)));
            ScheduleTime scheduleTime =
                    testFixtureBuilder.buildScheduleTime(
                            YEAR_2024_MONTH_04_DAY_17_HOUR_12_SCHEDULE_TIME(schedule.get(0)));
            String EARLIER_THAN_START_TIME = "202404171100"; // 시작 시간은 12시, 종료 시간을 11시

            assertThatThrownBy(
                            () ->
                                    scheduleTimeService.update(
                                            scheduleTime.getId(), EARLIER_THAN_START_TIME))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessageContaining(SCHEDULE_END_TIME_BEFORE_START_TIME.getMsg());
        }
    }

    @Nested
    @DisplayName("타임라인 조회할 시")
    class findSchedulesAndTimeInPeriod {

        @DisplayName("[정상 케이스] 성공적으로 조회한다.")
        @Test
        void success() {
            // Given
            User user = testFixtureBuilder.buildUser(COMPONO_USER());
            Long userId = user.getId();
            int year = 2024;
            int month = 04;
            int day = 17;
            List<Schedule> expected =
                    testFixtureBuilder.buildSchedule(
                            List.of(
                                    YEAR_2024_MONTH_04_DAY_17_SCHEDULE(userId),
                                    YEAR_2024_MONTH_04_DAY_17_AND_18_SCHEDULE(userId)));
            // When
            SchedulesWithTimesDTO actual =
                    scheduleTimeService.findSchedulesAndTimeInPeriod(userId, year, month, day);
            List<ScheduleWithTimesDTO> scheduleResponse = actual.scheduleWithTimes();

            // Then
            assertSoftly(
                    softly -> {
                        softly.assertThat(scheduleResponse).hasSize(2);
                        softly.assertThat(scheduleResponse.get(0).taskName())
                                .isEqualTo(expected.get(0).getTaskName());
                        softly.assertThat(scheduleResponse.get(1).taskName())
                                .isEqualTo(expected.get(1).getTaskName());
                    });
        }
    }
}
