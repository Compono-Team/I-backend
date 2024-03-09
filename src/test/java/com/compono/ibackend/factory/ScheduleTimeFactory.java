package com.compono.ibackend.factory;

import com.compono.ibackend.schedule.domain.ScheduleTime;
import java.time.LocalDateTime;
import org.springframework.test.util.ReflectionTestUtils;

public class ScheduleTimeFactory {
    private ScheduleTime createScheduleTime(Long scheduleTimeId) {
        ScheduleTime scheduleTime = new ScheduleTime();
        scheduleTime.setStartTime(LocalDateTime.of(2024, 3, 8, 12, 12));
        scheduleTime.setStopTime(LocalDateTime.of(2024, 3, 8, 13, 12));
        ReflectionTestUtils.setField(scheduleTime, "id", scheduleTimeId);
        return scheduleTime;
    }
}
