package com.compono.ibackend.schedule.controller;

import com.compono.ibackend.reservation.dto.response.PreReservationResponse;
import com.compono.ibackend.schedule.domain.ScheduleTime;
import com.compono.ibackend.schedule.dto.ScheduleWithScheduleTimeDTO;
import com.compono.ibackend.schedule.dto.request.TimeLineRequest;
import com.compono.ibackend.schedule.service.TimeLineService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedule")
public class ScheduleTimeController {

  private final TimeLineService timeLineService;

  @GetMapping("/timeline")
  public ResponseEntity<List<ScheduleWithScheduleTimeDTO>> getTimeLine(@RequestBody TimeLineRequest timeLineRequest) {
    return ResponseEntity.ok().body(timeLineService.findScheduleTimeWithinRange(timeLineRequest.userId(),timeLineRequest.time(),
        timeLineRequest.n()));
  }

  @GetMapping("/time/start/{scheduleId}")
  public ResponseEntity<ScheduleTime> saveScheduleTimeStart(@PathVariable Long scheduleId) {
    return ResponseEntity.ok().body(timeLineService.createScheduleTimeStart(scheduleId));
  }

  @PatchMapping("/time/stop/{scheduleTimeId}")
  public ResponseEntity<ScheduleTime> updateScheduleTimeStop(@PathVariable Long scheduleTimeId) {
    return ResponseEntity.ok().body(timeLineService.updateScheduleTimeStop(scheduleTimeId));
  }

}
