package com.compono.ibackend.schedule.enumType;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum TaskStatus {
    IN_PROGRESS,
    PAUSED,
    COMPLETED;

    public static String getAllType() {
        return Arrays.stream(values()).map(TaskStatus::name).collect(Collectors.joining(" || "));
    }
}
