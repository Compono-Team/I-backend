package com.compono.ibackend.common.fixtures;

import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.scheduleTime.domain.ScheduleTime;
import java.time.LocalDateTime;

public class ScheduleTimeFixtures {

    private static final LocalDateTime YEAR_2024_MONTH_04_DAY_17_HOUR_12_MIN_00_DATE =
            LocalDateTime.of(2024, 4, 17, 12, 00);
    private static final LocalDateTime YEAR_2024_MONTH_04_DAY_17_HOUR_13_MIN_00_DATE =
            LocalDateTime.of(2024, 4, 17, 13, 00);
    private static final LocalDateTime YEAR_2024_MONTH_04_DAY_17_HOUR_14_MIN_00_DATE =
            LocalDateTime.of(2024, 4, 17, 14, 00);
    private static final LocalDateTime YEAR_2024_MONTH_04_DAY_17_HOUR_17_MIN_00_DATE =
            LocalDateTime.of(2024, 4, 17, 17, 00);

    public static ScheduleTime YEAR_2024_MONTH_04_DAY_17_HOUR_12_AND_13_SCHEDULE_TIME(
            Schedule schedule) {
        ScheduleTime scheduleTime = new ScheduleTime();
        scheduleTime.setStartTime(YEAR_2024_MONTH_04_DAY_17_HOUR_12_MIN_00_DATE);
        scheduleTime.setStopTime(YEAR_2024_MONTH_04_DAY_17_HOUR_13_MIN_00_DATE);
        scheduleTime.setSchedule(schedule);
        return scheduleTime;
    }

    public static ScheduleTime YEAR_2024_MONTH_04_DAY_17_HOUR_14_AND_17_SCHEDULE_TIME(
            Schedule schedule) {
        ScheduleTime scheduleTime = new ScheduleTime();
        scheduleTime.setStartTime(YEAR_2024_MONTH_04_DAY_17_HOUR_14_MIN_00_DATE);
        scheduleTime.setStopTime(YEAR_2024_MONTH_04_DAY_17_HOUR_17_MIN_00_DATE);
        scheduleTime.setSchedule(schedule);
        return scheduleTime;
    }

    public static ScheduleTime YEAR_2024_MONTH_04_DAY_17_HOUR_12_SCHEDULE_TIME(Schedule schedule) {
        ScheduleTime scheduleTime = new ScheduleTime();
        scheduleTime.setStartTime(YEAR_2024_MONTH_04_DAY_17_HOUR_12_MIN_00_DATE);
        scheduleTime.setSchedule(schedule);
        return scheduleTime;
    }
}
