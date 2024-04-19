package com.compono.ibackend.schedule.dto;

import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.domain.ScheduleTime;
import java.util.List;
import java.util.stream.Collectors;

public record ScheduleWithTimesDTO(
        Long scheduleId, String taskName, List<ScheduleTimeDTO> scheduleTimeList) {

    public static ScheduleWithTimesDTO from(Schedule schedule) {
        List<ScheduleTime> scheduleTimes = schedule.getScheduleTimes();
        return new ScheduleWithTimesDTO(
                schedule.getId(),
                schedule.getTaskName(),
                scheduleTimes.stream().map(ScheduleTimeDTO::from).collect(Collectors.toList()));
    }
}
