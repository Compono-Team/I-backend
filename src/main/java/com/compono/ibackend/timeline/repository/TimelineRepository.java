package com.compono.ibackend.timeline.repository;

import com.compono.ibackend.timeline.domain.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimelineRepository extends JpaRepository<Timeline, Long> {

}
