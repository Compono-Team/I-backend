package com.compono.ibackend.schedule.service;

import static com.compono.ibackend.common.enumType.ErrorCode.NOT_FOUND_SCHEDULE;
import static com.compono.ibackend.common.enumType.ErrorCode.NOT_FOUND_SCHEDULE_TIME;
import static com.compono.ibackend.common.enumType.ErrorCode.SCHEDULE_END_TIME_BEFORE_START_TIME;

import com.compono.ibackend.common.exception.BadRequestException;
import com.compono.ibackend.schedule.domain.Period;
import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.domain.ScheduleTime;
import com.compono.ibackend.schedule.dto.SchedulesWithTimesDTO;
import com.compono.ibackend.schedule.dto.response.ScheduleTimeResponse;
import com.compono.ibackend.schedule.repository.ScheduleRepository;
import com.compono.ibackend.schedule.repository.ScheduleTimeRepository;
import com.compono.ibackend.schedule.service.parser.LocalDateTimeParser;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleTimeService {

    private final ScheduleTimeRepository scheduleTimeRepository;
    private final ScheduleRepository scheduleRepository;
    private final LocalDateTimeParser localDateTimeParser;

    // ScheduleTime 시작 시간 생성
    public ScheduleTimeResponse create(Long scheduleId, String dateString) {
        checkScheduleExist(scheduleId);

        Schedule schedule = scheduleRepository.findById(scheduleId).get();
        ScheduleTime scheduleTime = new ScheduleTime();

        LocalDateTime startDateTime = localDateTimeParser.parse(dateString);

        scheduleTime.setStartTime(startDateTime);
        scheduleTime.setSchedule(schedule);
        return ScheduleTimeResponse.from(scheduleTimeRepository.save(scheduleTime));
    }

    // scheduleTime 종료 시간 생성
    public ScheduleTimeResponse update(Long scheduleTimeId, String dateString) {
        checkScheduleTimeExist(scheduleTimeId);
        ScheduleTime scheduleTime = scheduleTimeRepository.findById(scheduleTimeId).get();
        LocalDateTime stopDateTime = localDateTimeParser.parse(dateString);
        if (stopDateTime.isBefore(scheduleTime.getStartTime())) {
            throw new BadRequestException(SCHEDULE_END_TIME_BEFORE_START_TIME);
        }

        scheduleTime.setStopTime(stopDateTime);
        return ScheduleTimeResponse.from(scheduleTimeRepository.save(scheduleTime));
    }

    private void checkScheduleTimeExist(Long scheduleTimeId) {
        if (notExistScheduleTime(scheduleTimeId)) {
            throw new BadRequestException(NOT_FOUND_SCHEDULE_TIME);
        }
    }

    private boolean notExistScheduleTime(Long scheduleTimeId) {
        return !scheduleTimeRepository.existsById(scheduleTimeId);
    }

    private void checkScheduleExist(Long scheduleId) {
        if (notExistSchedule(scheduleId)) {
            throw new BadRequestException(NOT_FOUND_SCHEDULE);
        }
    }

    private boolean notExistSchedule(Long scheduleId) {
        return !scheduleRepository.existsById(scheduleId);
    }

    // todo userId 토큰으로 통일하기
    @Transactional(readOnly = true)
    public SchedulesWithTimesDTO findSchedulesAndTimeInPeriod(
            long userId, int year, int month, int day) {

        Period period = Period.of(year, month, day);
        List<Schedule> schedules =
                scheduleRepository.findByUserIdAndDateWithinRange(
                        userId, period.startDateTime(), period.endDateTime());

        return SchedulesWithTimesDTO.from(schedules);
    }
}
