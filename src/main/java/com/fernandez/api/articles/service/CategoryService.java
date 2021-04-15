package com.fernandez.api.articles.service;

import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.CategoryDTO;
import com.fernandez.api.articles.model.Category;
import com.fernandez.api.articles.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    CategoryDTO findCategoryDtoById(Long categoryDTO);

    Category findCategoryById(Long categoryId);

    Category findCategoryBySlug(String slug);

    CategoryDTO findCategoryByIdOrSlug(Long categoryId,String slug);

    CategoryDTO findCategoryDtoBySlug(final String slug);

    List<CategoryDTO> categoryDTOList(ArticleDTO articleDTO);

    Page<CategoryDTO> findAll(String acceptLanguage, Pageable pageable);

    CategoryDTO save(CategoryDTO categoryDTO);

    void deleteCategoryById(Long id);

    void deleteArticleWithCategory(Long categoryId, Long articleId);
}
