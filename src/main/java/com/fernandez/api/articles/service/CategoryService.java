package com.fernandez.api.articles.service;

import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.CategoryDTO;
import com.fernandez.api.articles.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    CategoryDTO findByName ( String name );

    Category findCategoryById ( Long categoryDTO );

    CategoryDTO findCategoryDtoById ( Long categoryDTO );

    List < CategoryDTO > categoryDTOList ( ArticleDTO articleDTO );

    Page < CategoryDTO > findAll ( String acceptLanguage , Pageable pageable );

    CategoryDTO save ( CategoryDTO categoryDTO );

    void deleteById ( Long id );
}
