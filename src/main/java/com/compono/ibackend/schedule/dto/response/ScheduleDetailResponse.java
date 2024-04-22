package com.compono.ibackend.schedule.dto.response;

import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.enumType.RoutinePeriod;
import com.compono.ibackend.schedule.enumType.SchedulePriority;
import java.time.LocalDateTime;

public record ScheduleDetailResponse(
        Long id,
        String taskName,
        SchedulePriority priority,
        LocalDateTime startDate,
        LocalDateTime endDate,
        ScheduleDetailPointResponse point,
        Boolean isRoutine,
        RoutinePeriod routinePeriod,
        Boolean isMarked) {

    public static ScheduleDetailResponse of(Schedule schedule) {
        return new ScheduleDetailResponse(
                schedule.getId(),
                schedule.getTaskName(),
                schedule.getPriority(),
                schedule.getStartDate(),
                schedule.getEndDate(),
                ScheduleDetailPointResponse.from(schedule.getPoint()),
                schedule.getIsRoutine(),
                schedule.getRoutinePeriod(),
                schedule.getIsMarked());
    }
}
