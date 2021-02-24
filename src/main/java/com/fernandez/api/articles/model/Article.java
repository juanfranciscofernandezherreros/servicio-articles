package com.fernandez.api.articles.model;

import com.fernandez.api.articles.model.auditable.Auditable;
import com.fernandez.api.articles.validator.title.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "articles")
public class Article extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title" , nullable = false , unique = true)
    @Title
    private String title;

    @Column(name = "slug",nullable = false , unique = true)
    private String slug;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "content",nullable = false)
    private String content;

    @Column(name = "main_image", length = 600000 , nullable = false)
    private String mainImage;

    @Column(name = "language")
    private String language;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "articles_tags", joinColumns = {@JoinColumn(name = "articles_id")}, inverseJoinColumns = {@JoinColumn(name = "tagstranslations_id")})
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "articles_categories", joinColumns = {@JoinColumn(name = "articles_id")}, inverseJoinColumns = {@JoinColumn(name = "roles_id")})
    private Set<Category> categories = new HashSet<>();



}