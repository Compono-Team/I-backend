package com.compono.ibackend.schedule.repository.querydsl;

import com.compono.ibackend.schedule.domain.QPoint;
import com.compono.ibackend.schedule.domain.QSchedule;
import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.dto.response.ScheduleDetailPointResponse;
import com.compono.ibackend.schedule.dto.response.ScheduleDetailResponse;
import com.compono.ibackend.schedule.dto.response.ScheduleDetailWithTagResponse;
import com.compono.ibackend.schedule.dto.response.TagDetailResponse;
import com.compono.ibackend.tag.domain.QTag;
import com.compono.ibackend.tag.domain.QTagSchedule;
import com.compono.ibackend.user.domain.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ScheduleCustomRepositoryImpl extends QuerydslRepositorySupport
        implements ScheduleCustomRepository {

    public ScheduleCustomRepositoryImpl() {
        super(Schedule.class);
    }

    @Override
    public ScheduleDetailWithTagResponse findScheduleByUserAndScheduleId(
            User user, Long scheduleId) {
        QSchedule qSchedule = QSchedule.schedule;
        QTagSchedule qTagSchedule = QTagSchedule.tagSchedule;
        QTag qTag = QTag.tag;
        QPoint qPoint = QPoint.point;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qSchedule.user.eq(user));
        booleanBuilder.and(qSchedule.id.eq(scheduleId));
        booleanBuilder.and(qSchedule.isDeleted.eq(false));

        ScheduleDetailResponse schedule =
                from(qSchedule)
                        .select(
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
                                        qSchedule.isMarked))
                        .leftJoin(qSchedule.point, qPoint)
                        .where(booleanBuilder)
                        .fetchOne();

        List<TagDetailResponse> tags =
                from(qTag)
                        .select(
                                Projections.constructor(
                                        TagDetailResponse.class, qTag.id, qTag.name, qTag.color))
                        .leftJoin(qTagSchedule)
                        .on(qTagSchedule.schedule.id.eq(scheduleId))
                        .where(qTagSchedule.tag.eq(qTag))
                        .fetch();

        return ScheduleDetailWithTagResponse.from(schedule, tags);
    }
}
