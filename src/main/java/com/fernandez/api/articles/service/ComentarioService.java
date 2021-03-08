package com.fernandez.api.articles.service;

import com.fernandez.api.articles.dto.ComentariosDTO;

import java.util.List;

public interface ComentarioService {
    ComentariosDTO save(ComentariosDTO comentariosDTO);
    ComentariosDTO findCommentById(Long comentarioId);
    List<ComentariosDTO> findAllComentariosByBlogTranslationId(long comentarioId, long level, Long articleId, List<ComentariosDTO> comentariosList);
    void deleteById(Long commentId);
}
