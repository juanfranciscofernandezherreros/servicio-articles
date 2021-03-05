package com.fernandez.api.articles.service;


import com.fernandez.api.articles.dto.ArticleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {
    ArticleDTO save(ArticleDTO articleDTO);

    void deleteArticleById(Long id);

    ArticleDTO update(ArticleDTO articleDTO);

    Page<ArticleDTO> findAllArticles(String acceptLanguage, String name, List<String> tags, List<String> categoria, Pageable pageable);

    ArticleDTO findArticleBySlugOrId(String slug,Long articleId);
}
