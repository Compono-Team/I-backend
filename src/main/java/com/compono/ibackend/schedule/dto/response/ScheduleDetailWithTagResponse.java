package com.compono.ibackend.schedule.dto.response;

import java.util.List;

public record ScheduleDetailWithTagResponse(
        ScheduleDetailResponse schedule, List<TagDetailResponse> tags) {

    public static ScheduleDetailWithTagResponse from(
            ScheduleDetailResponse schedule, List<TagDetailResponse> tags) {
        return new ScheduleDetailWithTagResponse(schedule, tags);
    }
}
