package com.fernandez.api.articles.service;


import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.CategoryDTO;
import com.fernandez.api.articles.dto.TagDTO;
import com.fernandez.api.articles.wrapper.ArticleWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;


public interface ArticleService {
    ArticleDTO save(final ArticleDTO articleDTO) throws ParseException;

    void deleteArticleById(Long id);

    Page<ArticleDTO> findAllArticles(String acceptLanguage, ArticleWrapper articleWrapper, Pageable pageable);

    ArticleDTO findArticleBySlugOrId(String slug, Long articleId);

    Page findAllArticlesRandom(final String acceptLanguage, final Pageable pageable);

    List<CategoryDTO> findCategoriesFromArticle(final String acceptLanguage, final Long articleId);

    List<TagDTO> findTagsFromArticle(String acceptLanguage, Long articleId);
}
