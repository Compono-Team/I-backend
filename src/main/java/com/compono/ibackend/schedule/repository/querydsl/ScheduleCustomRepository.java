package com.compono.ibackend.schedule.repository.querydsl;

import com.compono.ibackend.schedule.dto.response.ScheduleDetailWithTagResponse;
import com.compono.ibackend.user.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleCustomRepository {

    ScheduleDetailWithTagResponse findScheduleByUserAndScheduleId(User user, Long scheduleId);
}
