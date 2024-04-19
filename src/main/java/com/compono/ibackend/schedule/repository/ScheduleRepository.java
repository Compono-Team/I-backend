package com.compono.ibackend.schedule.repository;

import com.compono.ibackend.schedule.domain.Schedule;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query(
            "select s from Schedule s where s.userId = :userId and (:startDate <= s.startDate and :endDate>=s.startDate) or (:startDate <= s.endDate and :endDate>=s.endDate)")
    List<Schedule> findByUserIdAndDateWithinRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
