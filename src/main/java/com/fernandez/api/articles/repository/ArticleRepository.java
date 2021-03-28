package com.fernandez.api.articles.repository;

import com.fernandez.api.articles.model.Article;
import com.fernandez.api.articles.model.Category;
import com.fernandez.api.articles.model.Tag;
import com.fernandez.api.articles.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository < Article, Long > {

    Article findArticleBySlug (String slug );

    Page < Article > findByTagsIn ( List < Tag > tags , Pageable pageable );

    Page < Article > findByCategoriesIn ( List < Category > categories , Pageable pageable );

    Page < Article > findArticleByLanguageAndTitleContaining( String acceptLanguage , String name , Pageable pageable );

    Page <Article> findAllByLanguage(String acceptLanguage, Pageable pageable);

    Page <Article> findArticleByLanguageAndUser ( String acceptLanguage , User user , Pageable pageable );
}
