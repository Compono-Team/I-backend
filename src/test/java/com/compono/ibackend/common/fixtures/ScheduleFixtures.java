package com.compono.ibackend.common.fixtures;

import java.time.LocalDateTime;

public class ScheduleFixtures {

    public static final String MEMO = "memo for schedule";
    public static final String EXERCISE = "운동";
    public static final String STUDY = "공부";

    public static final LocalDateTime YEAR_2024_MONTH_04_DAY_17_HOUR_8_MIN_00_DATE =
            LocalDateTime.of(2024, 04, 17, 8, 00);
    public static final LocalDateTime YEAR_2024_MONTH_04_DAY_17_HOUR_13_MIN_00_DATE =
            LocalDateTime.of(2024, 04, 17, 13, 00);

    public static final LocalDateTime YEAR_2024_MONTH_04_DAY_18_HOUR_13_MIN_00_DATE =
            LocalDateTime.of(2024, 04, 17, 13, 00);
    public static final LocalDateTime TODAY_PLUS_1_DATE = LocalDateTime.now().plusDays(1);
    public static final LocalDateTime TODAY_MINUS_1_DATE = LocalDateTime.now().minusDays(1);


}
