package com.compono.ibackend.schedule.service;

import static com.compono.ibackend.common.enumType.ErrorCode.NOT_FOUND_SCHEDULE;
import static com.compono.ibackend.common.enumType.ErrorCode.NOT_FOUND_SCHEDULE_TIME;

import com.compono.ibackend.common.exception.BadRequestException;
import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.domain.ScheduleTime;
import com.compono.ibackend.schedule.dto.ScheduleWithScheduleTimeDTO;
import com.compono.ibackend.schedule.dto.response.ScheduleTimeResponse;
import com.compono.ibackend.schedule.repository.ScheduleRepository;
import com.compono.ibackend.schedule.repository.ScheduleTimeRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TimeLineService {

    private final ScheduleTimeRepository scheduleTimeRepository;
    private final ScheduleRepository scheduleRepository;

    // ScheduleTime 시작 시간 생성
    public ScheduleTimeResponse createScheduleTimeStart(Long scheduleId) {
        LocalDateTime now = LocalDateTime.now();
        Schedule schedule =
                scheduleRepository
                        .findById(scheduleId)
                        .orElseThrow(() -> new BadRequestException(NOT_FOUND_SCHEDULE));
        ScheduleTime scheduleTime = new ScheduleTime();

        scheduleTime.setStartTime(now);
        scheduleTime.setSchedule(schedule);
        return ScheduleTimeResponse.from(scheduleTimeRepository.save(scheduleTime));
    }

    // scheduleTime 종료 시간 생성
    public ScheduleTimeResponse updateScheduleTimeStop(Long scheduleTimeId) {
        LocalDateTime now = LocalDateTime.now();
        ScheduleTime scheduleTime =
                scheduleTimeRepository
                        .findById(scheduleTimeId)
                        .orElseThrow(() -> new BadRequestException(NOT_FOUND_SCHEDULE_TIME));

        scheduleTime.setStopTime(now);
        return ScheduleTimeResponse.from(scheduleTimeRepository.save(scheduleTime));
    }

    // test용 n 시간 - + 타임라인 조회
    // todo n시간 확인해서 고정하기 //todo userId 토큰으로 통일하기
    public List<ScheduleWithScheduleTimeDTO> findScheduleTimeWithinRange(
            Long userId, LocalDateTime time, int n) {
        LocalDateTime startTime = time.minusHours(n);
        LocalDateTime endTime = time.plusHours(n);

        return scheduleTimeRepository.findWithinTimeRangeByUserId(userId, startTime, endTime);
    }
}
