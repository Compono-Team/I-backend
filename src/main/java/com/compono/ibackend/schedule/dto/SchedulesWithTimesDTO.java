package com.compono.ibackend.schedule.dto;

import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.domain.ScheduleTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record SchedulesWithTimesDTO(List<ScheduleWithTimesDTO> scheduleWithTimes) {
    public static SchedulesWithTimesDTO from(List<Schedule> schedules) {
        List<ScheduleWithTimesDTO> scheduleWithTimesDTOList = new ArrayList<>();
        for (Schedule schedule : schedules) {
            List<ScheduleTime> scheduleTimes = schedule.getScheduleTimes();

            List<ScheduleTimeDTO> scheduleTimeDTOList =
                    scheduleTimes.stream().map(ScheduleTimeDTO::from).collect(Collectors.toList());

            ScheduleWithTimesDTO scheduleWithTimesDTO =
                    new ScheduleWithTimesDTO(
                            schedule.getId(), schedule.getTaskName(), scheduleTimeDTOList);

            scheduleWithTimesDTOList.add(scheduleWithTimesDTO);
        }
        return new SchedulesWithTimesDTO(scheduleWithTimesDTOList);
    }
}
