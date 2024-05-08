package com.compono.ibackend.schedule.dto.request;

import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.enumType.RoutinePeriod;
import com.compono.ibackend.schedule.enumType.SchedulePriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public record ScheduleRequest(
        @NotBlank String taskName,
        @NotNull SchedulePriority priority,
        List<Long> tags,
        LocalDateTime startDate,
        LocalDateTime endDate,
        @NotNull SchedulePointRequest point,
        @NotNull boolean isRoutine,
        RoutinePeriod routinePeriod,
        @NotNull boolean isMarked) {

    public static Schedule of(
            Long userId,
            String taskName,
            SchedulePriority priority,
            LocalDateTime startDate,
            LocalDateTime endDate,
            boolean isRoutine,
            RoutinePeriod routinePeriod,
            boolean isMarked) {
        return Schedule.of(
                userId, taskName, priority, startDate, endDate, isRoutine, routinePeriod, isMarked);
    }

    public Schedule toEntity(Long userId) {
        return ScheduleRequest.of(
                userId, taskName, priority, startDate, endDate, isRoutine, routinePeriod, isMarked);
    }
}
