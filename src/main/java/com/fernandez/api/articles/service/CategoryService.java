package com.fernandez.api.articles.service;

import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO findByName(String name);

    List<CategoryDTO> categoryDTOList(ArticleDTO articleDTO);
}
