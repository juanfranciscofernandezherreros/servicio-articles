package com.fernandez.api.articles.repository;

import com.fernandez.api.articles.model.Comentarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CountCommentsBlogRepository extends JpaRepository < Comentarios, Long > {

    @Query ( "SELECT COUNT(c) FROM Comentarios c WHERE c.articleId=:articleId" )
    Long countCommentsFromArticle ( @Param ( "articleId" ) Long articleId );

}
