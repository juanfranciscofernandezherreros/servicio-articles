package com.fernandez.api.articles.util;

import com.fernandez.api.articles.dto.CategoryDTO;

public class CategoryDtoUtils {
    public static CategoryDTO mockCategroyDtoObject() {
        CategoryDTO catDto = new CategoryDTO();
        catDto.setName("Categoy1");
        catDto.setLanguage("es-ES");
        catDto.setSlug("slug1");
        return catDto;
    }
}
