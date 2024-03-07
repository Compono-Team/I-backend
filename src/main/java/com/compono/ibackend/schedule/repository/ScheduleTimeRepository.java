package com.compono.ibackend.schedule.repository;

import com.compono.ibackend.schedule.domain.ScheduleTime;
import com.compono.ibackend.schedule.repository.querydsl.ScheduleTimeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleTimeRepository extends JpaRepository<ScheduleTime,Long>, ScheduleTimeRepositoryCustom{

}
