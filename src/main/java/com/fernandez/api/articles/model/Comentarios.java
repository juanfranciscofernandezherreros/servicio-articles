package com.fernandez.api.articles.model;

import com.fernandez.api.articles.model.Article;
import com.fernandez.api.articles.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

    @JoinColumn(name = "article_id")
    private Long articleId;

    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Long parentId;

    private String dateTime;

    private boolean isanswer;

}