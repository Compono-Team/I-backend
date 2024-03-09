package com.compono.ibackend.tag.repository;

import com.compono.ibackend.tag.domain.TagSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagScheduleRepository extends JpaRepository<TagSchedule, Long> {}
