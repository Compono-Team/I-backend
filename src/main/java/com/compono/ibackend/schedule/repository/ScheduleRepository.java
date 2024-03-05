package com.compono.ibackend.schedule.repository;

import com.compono.ibackend.reservation.domain.PreReservation;
import com.compono.ibackend.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
