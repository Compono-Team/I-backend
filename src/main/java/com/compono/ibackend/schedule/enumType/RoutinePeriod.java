package com.compono.ibackend.schedule.enumType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoutinePeriod {
    ONCE(0, "ONCE"),
    DAILY(1, "DAILY"),
    EVERY_MONDAY(2, "EVERY_MONDAY");

    private final int code;
    private final String value;
}
