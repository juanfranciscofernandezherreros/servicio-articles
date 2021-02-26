package com.fernandez.api.articles.service;


import com.fernandez.api.articles.dto.ArticleDTO;

public interface ArticleService {
    ArticleDTO save(final ArticleDTO articleDTO);
    ArticleDTO findArticleBySlug(String slug);
    ArticleDTO findArticleById(Long id);
    void deleteArticleById(Long id);
}
