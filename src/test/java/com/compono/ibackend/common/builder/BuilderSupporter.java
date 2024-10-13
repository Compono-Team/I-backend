package com.compono.ibackend.common.builder;

import com.compono.ibackend.model.schedule.repository.ScheduleTimeRepository;
import com.compono.ibackend.model.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuilderSupporter {

    @Autowired private ScheduleTimeRepository scheduleTimeRepository;

    @Autowired private UserRepository userRepository;

    public ScheduleTimeRepository scheduleTimeRepository() {
        return scheduleTimeRepository;
    }

    public UserRepository userRepository() {
        return userRepository;
    }
}
