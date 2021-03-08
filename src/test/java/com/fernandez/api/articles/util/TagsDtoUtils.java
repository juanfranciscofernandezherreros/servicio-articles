package com.fernandez.api.articles.util;

import com.fernandez.api.articles.dto.TagDTO;

public class TagsDtoUtils {

    public static TagDTO mockTagsDtoObject() {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("name1º");
        tagDTO.setLanguage("es-ES");
        tagDTO.setSlug("slug1");
        return tagDTO;
    }

}

