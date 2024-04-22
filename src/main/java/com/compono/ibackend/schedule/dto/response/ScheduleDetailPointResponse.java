package com.compono.ibackend.schedule.dto.response;

import com.compono.ibackend.schedule.domain.Point;

public record ScheduleDetailPointResponse(Double longitude, Double latitude) {

    public static ScheduleDetailPointResponse from(Point point) {
        return new ScheduleDetailPointResponse(point.getLongitude(), point.getLatitude());
    }
}
