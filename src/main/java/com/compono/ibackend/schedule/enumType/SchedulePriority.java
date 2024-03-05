package com.compono.ibackend.schedule.enumType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SchedulePriority {
    P1(0, "상"),
    P2(1, "중"),
    P3(2, "하");

    private final int code;
    private final String value;
}
