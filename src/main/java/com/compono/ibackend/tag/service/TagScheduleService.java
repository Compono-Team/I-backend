package com.compono.ibackend.tag.service;

import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.tag.domain.Tag;
import com.compono.ibackend.tag.domain.TagSchedule;
import com.compono.ibackend.tag.repository.TagScheduleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagScheduleService {

    private final TagScheduleRepository tagScheduleRepository;

    /**
     * tagSchedule 생성하는 함수
     *
     * @param tagList
     * @param schedule
     */
    @Transactional
    public void addTagSchedule(List<Tag> tagList, Schedule schedule) {
        // 1. List<TagSchedule> 생성
        List<TagSchedule> tagSchedules =
                tagList.stream().map(tag -> new TagSchedule(tag, schedule)).toList();

        // 2. 저장
        tagScheduleRepository.saveAll(tagSchedules);
    }
}
