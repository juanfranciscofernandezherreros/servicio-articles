package com.fernandez.api.articles.repository;

import com.fernandez.api.articles.model.Comentarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository < Comentarios, Long > {
    List < Comentarios > findAllByParentIdAndArticleId ( long comentarioId , Long blogsTranslation );
}
