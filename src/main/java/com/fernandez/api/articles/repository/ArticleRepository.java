package com.fernandez.api.articles.repository;

import com.fernandez.api.articles.model.Article;
import com.fernandez.api.articles.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findArticleBySlug(String slug);

    List<Article> findByCategoriesIn(List<Category> categories);

}
