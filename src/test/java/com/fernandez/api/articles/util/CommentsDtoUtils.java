package com.fernandez.api.articles.util;

import com.fernandez.api.articles.dto.ComentariosDTO;

public class CommentsDtoUtils {
    public static ComentariosDTO mockComentariosDtoObjectWithUserFound() {
        ComentariosDTO comentariosDTO = new ComentariosDTO();
        comentariosDTO.setContenido("Contenido");
        comentariosDTO.setArticleId(1L);
        comentariosDTO.setParentId(0L);
        comentariosDTO.setLevel(0L);
        comentariosDTO.setIsanswer(false);
        return comentariosDTO;
    }
}
