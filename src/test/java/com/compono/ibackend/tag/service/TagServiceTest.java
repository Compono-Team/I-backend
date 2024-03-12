package com.compono.ibackend.tag.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import com.compono.ibackend.factory.TagFactory;
import com.compono.ibackend.tag.domain.Tag;
import com.compono.ibackend.tag.repository.TagRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("[비즈니스 로직] - 태그")
@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @InjectMocks private TagService tagService;
    @Mock private TagRepository tagRepository;

    @DisplayName("[정상 케이스] tag id list 에 대한 tag list를 조회한다.")
    @Test
    void findAllTagById() {
        List<Tag> tags = TagFactory.createTags();
        List<Long> tagIds = tags.stream().map(tag -> tag.getId()).toList();

        when(tagRepository.findAllById(tagIds)).thenReturn(tags);

        List<Tag> foundTags = tagService.findAllTagById(tagIds);

        assertNotNull(foundTags);
        assertEquals("", tags.size(), foundTags.size());
        assertTrue(tags.containsAll(foundTags));
    }
}
