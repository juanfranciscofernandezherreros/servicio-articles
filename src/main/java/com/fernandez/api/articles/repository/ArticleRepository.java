package com.fernandez.api.articles.repository;

import com.fernandez.api.articles.model.Article;
import com.fernandez.api.articles.model.Category;
import com.fernandez.api.articles.model.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @NotNull Optional<Article> findArticleBySlug(String slug);

    @NotNull Page<Article> findByTagsIn(List<Tag> tags, Pageable pageable);

    @NotNull Page<Article> findByCategoriesIn(List<Category> categories, Pageable pageable);

    @NotNull Page<Article> findArticleByLanguageAndTitle(String acceptLanguage, String name, Pageable pageable);

    @NotNull Page<Article> findAllByLanguage(String acceptLanguage, Pageable pageable);
}
