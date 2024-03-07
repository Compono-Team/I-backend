package com.compono.ibackend.schedule.dto;

import java.time.LocalDateTime;

public record ScheduleWithScheduleTimeDTO (

    String taskName,
    LocalDateTime stopTime,
    LocalDateTime startTime


  ){}
