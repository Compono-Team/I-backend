package com.compono.ibackend.schedule.service;

import com.compono.ibackend.schedule.domain.Point;
import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.dto.request.SchedulePointRequest;
import com.compono.ibackend.schedule.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService {

    private final PointRepository pointRepository;

    /**
     * 포인트 저장하는 함수 (+ Schedule 과의 연견관계 잇기)
     *
     * @param schedule
     * @param schedulePointRequest
     */
    @Transactional
    public Point addPoint(Schedule schedule, SchedulePointRequest schedulePointRequest) {
        Point point = schedulePointRequest.toEntity(schedule);
        return pointRepository.save(point);
    }
}
