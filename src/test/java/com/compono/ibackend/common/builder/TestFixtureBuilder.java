package com.compono.ibackend.common.builder;

import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.domain.ScheduleTime;
import com.compono.ibackend.user.domain.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestFixtureBuilder {

    @Autowired private BuilderSupporter builderSupporter;

    public ScheduleTime buildScheduleTime(ScheduleTime scheduleTime) {
        return builderSupporter.scheduleTimeRepository().save(scheduleTime);
    }

    public List<Schedule> buildSchedule(List<Schedule> schedules) {
        return builderSupporter.scheduleRepository().saveAll(schedules);
    }

    public User buildUser(User user) {
        return builderSupporter.userRepository().save(user);
    }
}
