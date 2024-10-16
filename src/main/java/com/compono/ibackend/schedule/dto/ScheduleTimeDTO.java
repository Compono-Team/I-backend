package com.compono.ibackend.schedule.dto;

import com.compono.ibackend.scheduleTime.domain.ScheduleTime;
import java.time.LocalDateTime;

public record ScheduleTimeDTO(Long id, LocalDateTime stopTime, LocalDateTime startTime) {

    public static ScheduleTimeDTO from(ScheduleTime scheduleTime) {
        return new ScheduleTimeDTO(
                scheduleTime.getId(), scheduleTime.getStopTime(), scheduleTime.getStartTime());
    }
}
