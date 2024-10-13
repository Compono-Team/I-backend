package com.compono.ibackend.model.schedule.dto.response;

import com.compono.ibackend.model.scheduleTime.domain.ScheduleTime;
import java.time.LocalDateTime;

public record ScheduleTimeResponse(Long id, LocalDateTime startTime, LocalDateTime stopTime) {

    public static ScheduleTimeResponse from(ScheduleTime scheduleTime) {
        return new ScheduleTimeResponse(
                scheduleTime.getId(), scheduleTime.getStartTime(), scheduleTime.getStopTime());
    }
}
