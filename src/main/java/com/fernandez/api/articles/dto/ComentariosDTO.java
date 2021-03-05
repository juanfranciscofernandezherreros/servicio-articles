package com.fernandez.api.articles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ComentariosDTO {

    private Long id;
    private String contenido;
    private UserDTO authorComment;
    private Long articleId;
    private Long parentId;
    private AuditDTO auditDTO;
    private ComentariosUserNotRegisteredDTO comentarioUserNotRegistered;
    private boolean isanswer;
    private Long level;

}
