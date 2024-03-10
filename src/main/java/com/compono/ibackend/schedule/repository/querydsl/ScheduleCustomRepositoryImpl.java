package com.compono.ibackend.schedule.repository.querydsl;

import com.compono.ibackend.schedule.domain.QPoint;
import com.compono.ibackend.schedule.domain.QSchedule;
import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.dto.response.ScheduleDetailPointResponse;
import com.compono.ibackend.schedule.dto.response.ScheduleDetailResponse;
import com.compono.ibackend.schedule.dto.response.ScheduleDetailTagResponse;
import com.compono.ibackend.tag.domain.QTag;
import com.compono.ibackend.tag.domain.QTagSchedule;
import com.compono.ibackend.user.domain.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ScheduleCustomRepositoryImpl extends QuerydslRepositorySupport
        implements ScheduleCustomRepository {

    public ScheduleCustomRepositoryImpl() {
        super(Schedule.class);
    }

    @Override
    public ScheduleDetailResponse findScheduleByUserAndScheduleId(User user, Long scheduleId) {
        QSchedule qSchedule = QSchedule.schedule;
        QTagSchedule qTagSchedule = QTagSchedule.tagSchedule;
        QTag qTag = QTag.tag;
        QPoint qPoint = QPoint.point;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qSchedule.user.eq(user));
        booleanBuilder.and(qSchedule.id.eq(scheduleId));
        booleanBuilder.and(qSchedule.isDeleted.eq(false));

        List<ScheduleDetailResponse> data =
                from(qSchedule)
                        .leftJoin(qSchedule.tagSchedules, qTagSchedule)
                        .leftJoin(qTagSchedule.tag, qTag)
                        .leftJoin(qSchedule.point, qPoint)
                        .where(booleanBuilder)
                        .transform(
                                GroupBy.groupBy(qSchedule.id, qTag.id)
                                        .list(
                                                Projections.constructor(
                                                        ScheduleDetailResponse.class,
                                                        qSchedule.id,
                                                        qSchedule.taskName,
                                                        qSchedule.priority,
                                                        qSchedule.startDate,
                                                        qSchedule.endDate,
                                                        Projections.constructor(
                                                                ScheduleDetailPointResponse.class,
                                                                qPoint.longitude,
                                                                qPoint.latitude),
                                                        qSchedule.isRoutine,
                                                        qSchedule.routinePeriod,
                                                        qSchedule.isMarked,
                                                        GroupBy.list(
                                                                Projections.list(
                                                                        Projections.constructor(
                                                                                ScheduleDetailTagResponse
                                                                                        .class,
                                                                                qTag.id,
                                                                                qTag.name,
                                                                                qTag.color))))));

        return data.get(0);
    }
}
