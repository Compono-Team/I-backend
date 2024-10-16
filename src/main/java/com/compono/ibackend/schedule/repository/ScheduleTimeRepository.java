package com.compono.ibackend.schedule.repository;

import com.compono.ibackend.scheduleTime.domain.ScheduleTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleTimeRepository extends JpaRepository<ScheduleTime, Long> {}
