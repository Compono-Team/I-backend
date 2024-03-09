package com.compono.ibackend.tag.service;

import com.compono.ibackend.tag.domain.Tag;
import com.compono.ibackend.tag.repository.TagRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;

    /**
     * idList로 Tag 를 함수
     *
     * @param ids
     * @return
     */
    public List<Tag> findAllTagById(List<Long> ids) {
        return tagRepository.findAllById(ids);
    }
}
