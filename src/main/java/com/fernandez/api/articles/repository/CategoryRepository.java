package com.fernandez.api.articles.repository;

import com.fernandez.api.articles.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository < Category, Long > {

    List < Category > findAllByLanguage ( String acceptLanguage );

    Category findByName ( String name );

    @Query ( value = "SELECT count(*) FROM ARTICLES_CATEGORIES WHERE ARTICLES_CATEGORIES.CATEGORIES_ID=?1", nativeQuery = true )
    Long countTotalArticlesFromCategory ( Category category );

    Optional<Category> findBySlug(String slug);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ARTICLES_CATEGORIES  where ARTICLES_CATEGORIES.ARTICLES_ID =:articlesId and ARTICLES_CATEGORIES.CATEGORIES_ID =:categoryId", nativeQuery = true)
    void deleteArticleFromCategory(@Param("categoryId") Long categoryId, @Param("articlesId") Long articlesId);
}
