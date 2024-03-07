package com.compono.ibackend.schedule.repository;

import com.compono.ibackend.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {



}
