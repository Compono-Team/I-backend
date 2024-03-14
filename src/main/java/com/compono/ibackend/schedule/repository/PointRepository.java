package com.compono.ibackend.schedule.repository;

import com.compono.ibackend.schedule.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {}
