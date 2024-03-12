package com.compono.ibackend.schedule.controller;

import com.compono.ibackend.common.security.impl.UserDetailsImpl;
import com.compono.ibackend.schedule.dto.request.ScheduleRequest;
import com.compono.ibackend.schedule.dto.response.ScheduleDetailWithTagResponse;
import com.compono.ibackend.schedule.dto.response.ScheduleResponse;
import com.compono.ibackend.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * Schedule 추가 API
     *
     * @param scheduleRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<ScheduleResponse> addSchedule(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody ScheduleRequest scheduleRequest) {
        return ResponseEntity.ok()
                .body(scheduleService.addSchedule(userDetails.getEmail(), scheduleRequest));
    }

    /**
     * 단일 스케줄 조회 API
     *
     * @param scheduleId
     * @return
     */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDetailWithTagResponse> getSchedule(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable(name = "scheduleId") Long scheduleId) {
        return ResponseEntity.ok()
                .body(scheduleService.findScheduleById(userDetails.getEmail(), scheduleId));
    }

    /**
     * 스케줄 삭제 API
     *
     * @param scheduleId
     * @return
     */
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Object> deleteSchedule(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable(name = "scheduleId") Long scheduleId) {
        scheduleService.deleteSchedule(userDetails.getEmail(), scheduleId);

        return ResponseEntity.ok().body(null);
    }
}
