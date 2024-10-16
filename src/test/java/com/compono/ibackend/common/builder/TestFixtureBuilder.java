package com.compono.ibackend.common.builder;

import com.compono.ibackend.scheduleTime.domain.ScheduleTime;
import com.compono.ibackend.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestFixtureBuilder {

    @Autowired private BuilderSupporter builderSupporter;

    public ScheduleTime buildScheduleTime(ScheduleTime scheduleTime) {
        return builderSupporter.scheduleTimeRepository().save(scheduleTime);
    }


    public User buildUser(User user) {
        return builderSupporter.userRepository().save(user);
    }
}
