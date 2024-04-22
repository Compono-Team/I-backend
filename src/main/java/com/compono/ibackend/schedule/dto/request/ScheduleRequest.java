package com.compono.ibackend.schedule.dto.request;

import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.enumType.RoutinePeriod;
import com.compono.ibackend.schedule.enumType.SchedulePriority;
import com.compono.ibackend.user.domain.User;
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
            User user,
            String taskName,
            SchedulePriority priority,
            LocalDateTime startDate,
            LocalDateTime endDate,
            boolean isRoutine,
            RoutinePeriod routinePeriod,
            boolean isMarked) {
        return new Schedule(
                user, taskName, priority, startDate, endDate, isRoutine, routinePeriod, isMarked);
    }

    public Schedule toEntity(User user) {
        return ScheduleRequest.of(
                user, taskName, priority, startDate, endDate, isRoutine, routinePeriod, isMarked);
    }
}
