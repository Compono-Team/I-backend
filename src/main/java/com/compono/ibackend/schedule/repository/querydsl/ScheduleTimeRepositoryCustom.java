package com.compono.ibackend.schedule.repository.querydsl;

import com.compono.ibackend.schedule.dto.ScheduleWithScheduleTimeDTO;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleTimeRepositoryCustom {
    List<ScheduleWithScheduleTimeDTO> findWithinTimeRangeByUserId(
            Long userId, LocalDateTime startTime, LocalDateTime endTime);
}
