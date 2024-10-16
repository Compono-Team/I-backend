package com.compono.ibackend.schedule.dto.response;


import com.compono.ibackend.location.domain.Location;

public record ScheduleDetailPointResponse(Double longitude, Double latitude) {

	public static ScheduleDetailPointResponse from(Location point) {
		return new ScheduleDetailPointResponse(point.getLongitude(), point.getLatitude());
	}
}
