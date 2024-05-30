package com.compono.ibackend.factory;

import com.compono.ibackend.schedule.domain.Point;
import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.enumType.RoutinePeriod;
import com.compono.ibackend.schedule.enumType.SchedulePriority;
import com.compono.ibackend.tag.domain.Tag;
import com.compono.ibackend.tag.domain.TagSchedule;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;

public class ScheduleFactory {

    public static Schedule createSchedule(Long userId, List<Tag> tags) {
        Schedule schedule =
                Schedule.of(
                        userId,
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

    public static Schedule createInvaildDateSchedule(Long userId, List<Tag> tags) {
        Schedule schedule =
                Schedule.of(
                        userId,
                        "알고리즘풀기",
                        SchedulePriority.P1,
                        null,
                        null,
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

    public static Schedule createInvaildRoutineSchedule(Long userId, List<Tag> tags) {
        Schedule schedule =
                Schedule.of(
                        userId,
                        "알고리즘풀기",
                        SchedulePriority.P1,
                        LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().plusDays(1).plusHours(1),
                        true,
                        RoutinePeriod.NONE,
                        true);
        ReflectionTestUtils.setField(schedule, "id", 1L);

        Point.of(schedule, 36.15, 105.456);

        tags.forEach(
                tag -> {
                    new TagSchedule(tag, schedule);
                });

        return schedule;
    }

    public static Schedule createInvaildNotRoutineSchedule(Long userId, List<Tag> tags) {
        Schedule schedule =
                Schedule.of(
                        userId,
                        "알고리즘풀기",
                        SchedulePriority.P1,
                        LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().plusDays(1).plusHours(1),
                        false,
                        RoutinePeriod.TUESDAY,
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
