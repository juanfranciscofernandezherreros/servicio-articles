package com.fernandez.api.articles.model;

import com.fernandez.api.articles.model.auditable.Audit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comentarios")
public class Comentarios implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenido;

    @OneToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User authorComment;

    private Long articleId;

    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Long parentId;

    @Embedded
    private Audit audit = new Audit();

    private boolean isanswer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "comentarios_usernotregistered" )
    private ComentariosUserNotRegistered comentarioUserNotRegistered;

}