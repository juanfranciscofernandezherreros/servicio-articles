package com.fernandez.api.articles.service;

import com.fernandez.api.articles.dto.ArticleDto;
import com.fernandez.api.articles.dto.CategoryDto;
import com.fernandez.api.articles.model.Category;
import java.util.List;

public interface CategoryService {
    CategoryDto findByName(String name);

    Category findCategoryById(Long categoryDto);

    List<CategoryDto> categoryDtoList(ArticleDto articleDto);
}
