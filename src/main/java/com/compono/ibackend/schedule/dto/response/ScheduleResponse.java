package com.compono.ibackend.schedule.dto.response;

import com.compono.ibackend.schedule.domain.Schedule;

public record ScheduleResponse(Long id, String taskName) {

    public static ScheduleResponse from(Schedule schedule) {
        return new ScheduleResponse(schedule.getId(), schedule.getTaskName());
    }
}
