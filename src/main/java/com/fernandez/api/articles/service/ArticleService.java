package com.fernandez.api.articles.service;


import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.wrapper.ArticleWrapper;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {
    ArticleDTO save(final ArticleDTO articleDTO);
    ArticleDTO findArticleBySlug(String slug);
    ArticleDTO findArticleById(Long id);
    void deleteArticleById(Long id);
    ArticleDTO update(ArticleDTO articleDTO);
}
