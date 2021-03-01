package com.fernandez.api.articles.service;


import com.fernandez.api.articles.dto.ArticleDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ArticleService {
    ArticleDto save(final ArticleDto articleDto);

    void deleteArticleById(Long id);

    ArticleDto update(ArticleDto articleDto);

    Page<ArticleDto> findAllArticles(String acceptLanguage, String name, List<String> tags, List<String> categoria, Pageable pageable);

    ArticleDto findArticleBySlugOrId(String slug, Long articleId);
}
