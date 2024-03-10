package com.compono.ibackend.schedule.dto.response;

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
        Boolean isMarked) {}
