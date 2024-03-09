package com.compono.ibackend.schedule.service;

import com.compono.ibackend.common.enumType.ErrorCode;
import com.compono.ibackend.common.exception.CustomException;
import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.dto.request.ScheduleRequest;
import com.compono.ibackend.schedule.dto.response.ScheduleDetailResponse;
import com.compono.ibackend.schedule.dto.response.ScheduleResponse;
import com.compono.ibackend.schedule.enumType.RoutinePeriod;
import com.compono.ibackend.schedule.repository.ScheduleRepository;
import com.compono.ibackend.tag.domain.Tag;
import com.compono.ibackend.tag.service.TagScheduleService;
import com.compono.ibackend.tag.service.TagService;
import com.compono.ibackend.user.domain.User;
import com.compono.ibackend.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final PointService pointService;
    private final TagService tagService;
    private final TagScheduleService tagScheduleService;
    private final UserService userService;

    /***
     * 스케줄 저장하는 함수
     * @param scheduleRequest
     * @return
     */
    @Transactional
    public ScheduleResponse addSchedule(String email, ScheduleRequest scheduleRequest) {
        try {
            // 1. 유효성 검사
            User user = userService.findUserByEmail(email);
            if (!validateScheduleRequest(scheduleRequest)) {
                throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_SCHEDULE);
            }

            // 2. tag 존재 확인
            List<Tag> tags = tagService.findAllTagById(scheduleRequest.tags());
            if (tags.size() != scheduleRequest.tags().size()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_FOUND_TAG_ID);
            }

            // 3. Schedule 저장
            Schedule schedule = scheduleRequest.toEntity(user);
            scheduleRepository.save(schedule);

            // 4. Point 저장 및 Schedule과 연관관계 연결
            pointService.addPoint(schedule, scheduleRequest.point());

            // 5. tag 연관관계 연결
            tagScheduleService.addTagSchedule(tags, schedule);

            return ScheduleResponse.from(schedule);
        } catch (Exception ex) {
            throw new CustomException(ex);
        }
    }

    /**
     * scheduleRequest 유효성 검사 함수
     *
     * @param scheduleRequest
     */
    private boolean validateScheduleRequest(ScheduleRequest scheduleRequest) {
        if (scheduleRequest.startDate() == null && scheduleRequest.endDate() == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_SCHEDULE);
        }

        if ((scheduleRequest.isRoutine() && scheduleRequest.routinePeriod() == RoutinePeriod.NONE)
                || (!scheduleRequest.isRoutine()
                        && scheduleRequest.routinePeriod() != RoutinePeriod.NONE)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_SCHEDULE);
        }

        return true;
    }

    /**
     * 스케줄 단일 조회 함수
     *
     * @param scheduleId
     * @return
     */
    public ScheduleDetailResponse findScheduleById(String email, Long scheduleId) {
        // 1. 사용자에게 등록된 스케줄인지 확인
        User user = userService.findUserByEmail(email);

        // 2. 조회
        return null;
    }
}
