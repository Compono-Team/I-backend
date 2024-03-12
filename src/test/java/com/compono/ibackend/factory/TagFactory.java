package com.compono.ibackend.factory;

import com.compono.ibackend.tag.domain.Tag;

public class TagFactory {

    public static Tag createTag(String tagName) {
        return Tag.from(tagName, "#ffffff");
    }
}
