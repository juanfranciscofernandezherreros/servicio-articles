package com.fernandez.api.articles.service;

import com.fernandez.api.articles.dto.ComentariosDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComentarioService {
    ComentariosDTO save(ComentariosDTO comentariosDTO) throws Exception;
    ComentariosDTO findCommentById(Long comentarioId);
    List<ComentariosDTO> findAllComentariosByBlogTranslationId(long comentarioId, long level, Long articleId, List<ComentariosDTO> comentariosList);
    void deleteById(Long commentId);
    Page<ComentariosDTO> findAllComments ( String acceptLanguage , Pageable pageable );
}
