package com.fernandez.api.articles.service;


import com.fernandez.api.articles.dto.ArticleDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ArticleService {
    ArticleDTO save(final ArticleDTO articleDto);

    void deleteArticleById(Long id);

    ArticleDTO update(ArticleDTO articleDto);

    Page<ArticleDTO> findAllArticles(String acceptLanguage, String name, List<String> tags, List<String> categoria, Pageable pageable);

    ArticleDTO findArticleBySlugOrId(String slug, Long articleId);
}
