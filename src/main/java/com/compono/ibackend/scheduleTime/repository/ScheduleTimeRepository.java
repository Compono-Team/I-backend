package com.compono.ibackend.scheduleTime.repository;

import com.compono.ibackend.scheduleTime.domain.ScheduleTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleTimeRepository extends JpaRepository<ScheduleTime, Long> {

}
