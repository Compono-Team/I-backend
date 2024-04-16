package com.compono.ibackend.schedule.repository;

import static com.compono.ibackend.common.fixtures.ScheduleFixtures.TODAY_AND_TOMORROW_SCHEDULE;
import static com.compono.ibackend.common.fixtures.ScheduleFixtures.TODAY_SCHEDULE;
import static com.compono.ibackend.common.fixtures.UserFixtures.COMPONO_USER;
import static org.junit.jupiter.api.Assertions.*;

import com.compono.ibackend.common.annotation.RepositoryTest;
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
        User MOCK_USER = testFixtureBuilder.buildUser(COMPONO_USER());
        Long userId = MOCK_USER.getId();
        List<Schedule> expectedSchedules =
                testFixtureBuilder.buildSchedule(
                        List.of(TODAY_AND_TOMORROW_SCHEDULE(userId), TODAY_SCHEDULE(userId)));

        LocalDateTime todayStart = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime todayEnd = todayStart.plusDays(1).minusSeconds(1);

        List<Schedule> actualSchedules =
                scheduleRepository.findByUserIdAndDateWithinRange(userId, todayStart, todayEnd);

        Assertions.assertThat(actualSchedules)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedSchedules);
    }
}
