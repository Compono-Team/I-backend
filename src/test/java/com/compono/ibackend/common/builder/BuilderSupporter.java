package com.compono.ibackend.common.builder;

import com.compono.ibackend.schedule.repository.ScheduleRepository;
import com.compono.ibackend.schedule.repository.ScheduleTimeRepository;
import com.compono.ibackend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuilderSupporter {

    @Autowired private ScheduleTimeRepository scheduleTimeRepository;

    @Autowired private ScheduleRepository scheduleRepository;

    @Autowired private UserRepository userRepository;

    public ScheduleTimeRepository scheduleTimeRepository() {
        return scheduleTimeRepository;
    }

    public ScheduleRepository scheduleRepository() {
        return scheduleRepository;
    }

    public UserRepository userRepository() {
        return userRepository;
    }
}
