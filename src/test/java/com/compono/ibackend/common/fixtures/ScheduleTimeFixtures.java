package com.compono.ibackend.common.fixtures;

import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.domain.ScheduleTime;
import java.time.LocalDateTime;

public class ScheduleTimeFixtures {

    private static final LocalDateTime DATE_2024_03_22_12_00 =
            LocalDateTime.of(2024, 3, 22, 12, 00);
    private static final LocalDateTime DATE_2024_03_22_13_00 =
            LocalDateTime.of(2024, 3, 22, 13, 00);

    public static ScheduleTime DATE_2024_03_22_12_00_SCHEDULE_TIME(Schedule schedule) {
        ScheduleTime scheduleTime = new ScheduleTime();
        scheduleTime.setStartTime(DATE_2024_03_22_12_00);
        scheduleTime.setStopTime(DATE_2024_03_22_13_00);
        scheduleTime.setSchedule(schedule);
        return scheduleTime;
    }
}
