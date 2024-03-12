package com.compono.ibackend.factory;

import com.compono.ibackend.schedule.domain.Point;
import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.enumType.RoutinePeriod;
import com.compono.ibackend.schedule.enumType.SchedulePriority;
import com.compono.ibackend.tag.domain.Tag;
import com.compono.ibackend.tag.domain.TagSchedule;
import com.compono.ibackend.user.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;

public class ScheduleFactory {

    public static Schedule createSchedule(User user, List<Tag> tags) {
        Schedule schedule =
                Schedule.of(
                        user,
                        "알고리즘풀기",
                        SchedulePriority.P1,
                        LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().plusDays(1).plusHours(1),
                        true,
                        RoutinePeriod.EVERYDAY,
                        true);
        ReflectionTestUtils.setField(schedule, "id", 1L);

        Point.of(schedule, 36.15, 105.456);

        tags.forEach(
                tag -> {
                    new TagSchedule(tag, schedule);
                });

        return schedule;
    }
}
