package com.fernandez.api.articles.repository;

import com.fernandez.api.articles.model.Article;
import com.fernandez.api.articles.model.Category;
import com.fernandez.api.articles.model.Tag;
import com.fernandez.api.articles.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ArticleRepository extends JpaRepository < Article, Long > {

    Article findArticleBySlug (String slug );
    
    Page<Article> findByCategoriesInOrderByAudit_CreatedOnDesc(List<Category> categories, Pageable pageable);

    Page<Article> findByTagsInOrderByAudit_CreatedOnDesc(List<Tag> tags, Pageable pageable);


    @Query ("SELECT p FROM #{#entityName} p WHERE p.title LIKE %?2% AND p.language = ?1 ORDER BY audit.createdOn DESC")
    Page < Article > findArticleByLanguageAndTitleContaining( String acceptLanguage , String title , Pageable pageable );

    @Query ("SELECT p FROM #{#entityName} p WHERE  p.language = :acceptLanguage ORDER BY audit.createdOn DESC")
    Page <Article> findAllByLanguage(String acceptLanguage, Pageable pageable);

    @Query ("SELECT p FROM #{#entityName} p WHERE  p.language = :acceptLanguage AND p.user = :user ORDER BY audit.createdOn DESC")
    Page <Article> findArticleByLanguageAndUser ( String acceptLanguage , User user , Pageable pageable );
    
}

