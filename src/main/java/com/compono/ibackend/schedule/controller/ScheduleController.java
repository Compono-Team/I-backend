package com.compono.ibackend.schedule.controller;

import com.compono.ibackend.schedule.dto.request.ScheduleRequest;
import com.compono.ibackend.schedule.dto.response.ScheduleResponse;
import com.compono.ibackend.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
            // @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody ScheduleRequest scheduleRequest) {
        return ResponseEntity.ok().body(scheduleService.addSchedule(scheduleRequest));
    }
}
