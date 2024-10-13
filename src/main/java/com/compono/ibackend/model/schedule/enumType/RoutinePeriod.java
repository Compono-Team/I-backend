package com.compono.ibackend.model.schedule.enumType;

import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoutinePeriod {
    NONE(0, "NONE"),
    MONDAY(1, "월"),
    TUESDAY(2, "화"),
    WEDNESDAY(3, "수"),
    THURSDAY(4, "목"),
    FRIDAY(5, "금"),
    SATURDAY(6, "토"),
    EXCEPT_PUBLIC_HOLIDAY(7, "공휴일 제외"),
    EVERYDAY(8, "매일"),
    WEEKDAY(9, "평일"),
    WEEKEND(10, "주말");

    private final int code;
    private final String value;

    public static String getAllType() {
        return Arrays.stream(values()).map(RoutinePeriod::name).collect(Collectors.joining(" || "));
    }
}
