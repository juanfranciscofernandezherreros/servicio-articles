package com.fernandez.api.articles.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    @NotNull
    private String content;

    @Column(name = "article_id")
    @NotNull
    private Long articleId;

    @Column(name = "user_id")
    @NotNull
    private Long userId;

    @Column(name = "is_answer")
    private boolean isRespuesta;

    @Column(name = "comment_id_answer")
    @NotNull
    private Long comentarioIdRespuesta;

}
