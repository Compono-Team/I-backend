package com.compono.ibackend.schedule.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record Period(LocalDateTime startDateTime, LocalDateTime endDateTime) {

    private static final int FIRST_DAY = 1;
    private static final LocalTime START_TIME = LocalTime.of(0, 0, 0);
    private static final int NEXT_MONTH_OFFSET = 1;
    private static int NEXT_DAY_OFFSET = 1;
    private static int NEXT_SECOND_OFFSET = 1;

    public static Period of(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, FIRST_DAY);
        LocalDate endDate = startDate.plusMonths(NEXT_MONTH_OFFSET).withDayOfMonth(FIRST_DAY);

        return new Period(
                LocalDateTime.of(startDate, START_TIME), LocalDateTime.of(endDate, START_TIME));
    }

    public static Period of(int year, int month, int day) {
        LocalDate startDate = LocalDate.of(year, month, day);
        LocalDate endDate = startDate.plusDays(NEXT_DAY_OFFSET);

        return new Period(
                LocalDateTime.of(startDate, START_TIME), LocalDateTime.of(endDate, START_TIME));
    }
}
