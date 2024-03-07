package com.compono.ibackend.schedule.repository.querydsl;

import com.compono.ibackend.schedule.domain.QSchedule;
import com.compono.ibackend.schedule.domain.QScheduleTime;
import com.compono.ibackend.schedule.domain.ScheduleTime;
import com.compono.ibackend.schedule.dto.ScheduleWithScheduleTimeDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ScheduleTimeRepositoryCustomImpl extends QuerydslRepositorySupport implements ScheduleTimeRepositoryCustom {

  public ScheduleTimeRepositoryCustomImpl(){
    super(ScheduleTime.class);
  }

  @Override
  public List<ScheduleWithScheduleTimeDTO> findWithinTimeRangeByUserId(Long userId, LocalDateTime startTime,
      LocalDateTime endTime) {
    QScheduleTime qScheduleTime = QScheduleTime.scheduleTime;
    QSchedule qSchedule = QSchedule.schedule;

    return from(qScheduleTime)
        .select(Projections.constructor(ScheduleWithScheduleTimeDTO.class, qScheduleTime, qSchedule))
        .join(qScheduleTime.schedule,qSchedule)
        .where(qSchedule.user.id.eq(userId).and(qScheduleTime.startTime.between(startTime,endTime)
            .or(qScheduleTime.stopTime.between(startTime,endTime))))
        .fetch();
  }
}
