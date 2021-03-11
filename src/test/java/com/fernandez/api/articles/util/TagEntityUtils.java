package com.fernandez.api.articles.util;

import com.fernandez.api.articles.model.Tag;

import java.util.Optional;

public class TagEntityUtils {
    public static Optional<Tag> mockTagEntity() {
        Tag tag = new Tag();
        tag.setName("tag1");
        tag.setLanguage("es-ES");
        tag.setSlug("slug1");
        return Optional.ofNullable(tag);
    }
}
