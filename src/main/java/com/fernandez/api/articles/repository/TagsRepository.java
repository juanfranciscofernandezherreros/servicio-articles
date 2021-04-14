package com.fernandez.api.articles.repository;

import com.fernandez.api.articles.model.Category;
import com.fernandez.api.articles.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TagsRepository extends JpaRepository < Tag, Long > {

    Tag findByNameAndLanguage ( String name , String language );

    Page < Tag > findAllByLanguage ( String acceptLanguage , Pageable pageable );

    List < Tag > findAllByLanguage ( String acceptLanguage );

    Tag findBySlug(String slug);

    @Query( value = "SELECT count(*) FROM articles_tags WHERE articles_tags.tags_id=?1", nativeQuery = true )
    Long countTotalArticlesFromTag ( Tag tag );

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ARTICLES_TAGS  where ARTICLES_TAGS.TAGS_ID =:tagsId and ARTICLES_TAGS.ARTICLES_ID =:articlesId", nativeQuery = true)
    void deleteArticleFromTag(@Param("tagsId") Long tagsId, @Param("articlesId") Long articlesId);
}
