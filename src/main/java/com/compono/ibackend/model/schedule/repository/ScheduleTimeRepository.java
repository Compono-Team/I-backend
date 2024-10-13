package com.compono.ibackend.model.schedule.repository;

import com.compono.ibackend.model.scheduleTime.domain.ScheduleTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleTimeRepository extends JpaRepository<ScheduleTime, Long> {}
