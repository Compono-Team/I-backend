package com.compono.ibackend.factory;

import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.enumType.TaskStatus;
import java.time.LocalDateTime;
import org.springframework.test.util.ReflectionTestUtils;

public class ScheduleFactory {

    public static Schedule createSchedule(Long scheduleId) {
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
                        UserFactory.createUser("test@test.com"));

        ReflectionTestUtils.setField(schedule, "id", scheduleId);

        return schedule;
    }
}
