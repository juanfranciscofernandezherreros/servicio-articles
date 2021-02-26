package com.fernandez.api.articles.repository;

import com.fernandez.api.articles.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findArticleByLanguageAndSlug(String language, String slug);
}
