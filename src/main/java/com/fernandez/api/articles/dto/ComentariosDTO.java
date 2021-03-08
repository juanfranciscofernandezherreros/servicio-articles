package com.fernandez.api.articles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ComentariosDTO {

    private Long id;

    @NonNull
    @NotEmpty
    private String contenido;

    @Valid
    private UserDTO authorComment;

    @NonNull
    private Long  articleId;

    @NonNull
    private Long parentId;

    private AuditDTO auditDTO;

    private ComentariosUserNotRegisteredDTO comentarioUserNotRegistered;

    private boolean isanswer;

    @NonNull
    private Long level;

}
