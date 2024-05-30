package com.compono.ibackend.schedule.repository.querydsl;

import com.compono.ibackend.schedule.dto.response.ScheduleDetailWithTagResponse;

public interface ScheduleCustomRepository {

    ScheduleDetailWithTagResponse findScheduleByUserIdAndScheduleId(Long userId, Long scheduleId);
}
