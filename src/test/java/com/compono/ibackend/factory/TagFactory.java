package com.compono.ibackend.factory;

import com.compono.ibackend.tag.domain.Tag;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;

public class TagFactory {

    public static Tag createTag(String tagName) {
        return Tag.from(tagName, "#ffffff");
    }

    public static List<Tag> createTags() {
        Tag tag1 = TagFactory.createTag("공부");
        Tag tag2 = TagFactory.createTag("자기계발");
        ReflectionTestUtils.setField(tag1, "id", 1L);
        ReflectionTestUtils.setField(tag2, "id", 2L);

        return List.of(tag1, tag2);
    }
}
