package com.compono.ibackend.common.fixtures;

import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.enumType.TaskStatus;
import java.time.LocalDateTime;

public class ScheduleFixtures {

    public static final String MEMO = "memo for schedule";
    public static final String TASK_NAME_ = "오늘 일정";

    public static final LocalDateTime TODAY_DATE = LocalDateTime.now();
    public static final LocalDateTime TODAY_PLUS_1_DATE = LocalDateTime.now().plusDays(1);
    public static final LocalDateTime TODAY_MINUS_1_DATE = LocalDateTime.now().minusDays(1);

    public static Schedule TODAY_SCHEDULE(Long userId) {
        Schedule schedule =
                Schedule.of(
                        TASK_NAME_,
                        false,
                        TODAY_DATE,
                        TODAY_DATE,
                        MEMO,
                        TaskStatus.IN_PROGRESS,
                        1,
                        false,
                        userId);

        return schedule;
    }

    public static Schedule TODAY_AND_TOMORROW_SCHEDULE(Long userId) {
        Schedule schedule =
                Schedule.of(
                        TASK_NAME_,
                        false,
                        TODAY_DATE,
                        TODAY_PLUS_1_DATE,
                        MEMO,
                        TaskStatus.IN_PROGRESS,
                        1,
                        false,
                        userId);

        return schedule;
    }

    public static Schedule YESTERDAY_SCHEDULE(Long userId) {
        Schedule schedule =
                Schedule.of(
                        TASK_NAME_,
                        false,
                        TODAY_MINUS_1_DATE,
                        TODAY_MINUS_1_DATE,
                        MEMO,
                        TaskStatus.IN_PROGRESS,
                        1,
                        false,
                        userId);

        return schedule;
    }
}
