package com.compono.ibackend.schedule.controller;

import com.compono.ibackend.schedule.dto.SchedulesWithTimesDTO;
import com.compono.ibackend.schedule.dto.response.ScheduleTimeResponse;
import com.compono.ibackend.schedule.service.ScheduleTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedule")
public class ScheduleTimeController {

    private final ScheduleTimeService scheduleTimeService;

    @GetMapping(
            value = "/timeline",
            params = {"userId", "year", "month", "day"})
    public ResponseEntity<SchedulesWithTimesDTO> getTimeLine(
            Long userId, int year, int month, int day) {
        return ResponseEntity.ok()
                .body(scheduleTimeService.findSchedulesAndTimeInPeriod(userId, year, month, day));
    }

    @PostMapping("/time/start/{scheduleId}")
    public ResponseEntity<ScheduleTimeResponse> saveScheduleTimeStart(
            @PathVariable(name = "scheduleId") Long scheduleId, String dateString) {
        return ResponseEntity.ok().body(scheduleTimeService.create(scheduleId, dateString));
    }

    @PatchMapping("/time/stop/{scheduleTimeId}")
    public ResponseEntity<ScheduleTimeResponse> updateScheduleTimeStop(
            @PathVariable(name = "scheduleTimeId") Long scheduleTimeId, String dateString) {
        return ResponseEntity.ok().body(scheduleTimeService.update(scheduleTimeId, dateString));
    }
}
