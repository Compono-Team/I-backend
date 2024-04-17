package com.compono.ibackend.schedule.repository;

import static com.compono.ibackend.common.fixtures.ScheduleFixtures.YEAR_2024_MONTH_04_DAY_17_AND_18_SCHEDULE;
import static com.compono.ibackend.common.fixtures.ScheduleFixtures.YEAR_2024_MONTH_04_DAY_17_SCHEDULE;
import static com.compono.ibackend.common.fixtures.UserFixtures.COMPONO_USER;

import com.compono.ibackend.common.RepositoryTest;
import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.user.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ScheduleRepositoryTest extends RepositoryTest {

    @Autowired private ScheduleRepository scheduleRepository;

    @Test
    void findByUserIdAndDateWithinRange() {
        User user = testFixtureBuilder.buildUser(COMPONO_USER());
        Long userId = user.getId();
        List<Schedule> expectedSchedules =
                testFixtureBuilder.buildSchedule(
                        List.of(
                                YEAR_2024_MONTH_04_DAY_17_SCHEDULE(userId),
                                YEAR_2024_MONTH_04_DAY_17_AND_18_SCHEDULE(userId)));

        LocalDateTime todayStart = LocalDateTime.of(2024, 4, 17, 00, 00);
        LocalDateTime todayEnd = LocalDateTime.of(2024, 4, 17, 23, 59, 59);

        List<Schedule> actualSchedules =
                scheduleRepository.findByUserIdAndDateWithinRange(userId, todayStart, todayEnd);

        Assertions.assertThat(actualSchedules)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedSchedules);
    }
}
