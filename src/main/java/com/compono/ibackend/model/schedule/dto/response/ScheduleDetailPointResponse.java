package com.compono.ibackend.model.schedule.dto.response;

import com.compono.ibackend.model.location.domain.Location;

public record ScheduleDetailPointResponse(Double longitude, Double latitude) {

    public static ScheduleDetailPointResponse from(Location location) {
        return new ScheduleDetailPointResponse(location.getLongitude(), location.getLatitude());
    }
}
