package com.compono.ibackend.schedule.dto.request;

import com.compono.ibackend.schedule.domain.Point;
import com.compono.ibackend.schedule.domain.Schedule;
import jakarta.validation.constraints.NotNull;

public record SchedulePointRequest(
        @NotNull(message = "longitude는 null일 수 없습니다.") double longitude,
        @NotNull(message = "latitude는 null일 수 없습니다.") double latitude) {

    public static Point of(Schedule schedule, double longitude, double latitude) {
        return new Point(schedule, longitude, latitude);
    }

    public Point toEntity(Schedule schedule) {
        return SchedulePointRequest.of(schedule, longitude, latitude);
    }
}
