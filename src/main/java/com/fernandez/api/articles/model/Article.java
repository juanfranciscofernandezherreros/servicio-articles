package com.fernandez.api.articles.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "url_seo")
    @NotNull
    private String urlSeo;

    @Column(name = "description")
    @NotNull
    private String description;

    @Column(name = "content")
    @NotNull
    private String content;

    @Column(name = "main_image", length = 600000)
    @NotNull
    private String mainImage;

    @OneToOne
    @JoinColumn(name = "language_id", referencedColumnName = "id")
    private Language language;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "articles_tags", joinColumns = { @JoinColumn(name = "articles_id") }, inverseJoinColumns = { @JoinColumn(name = "tagstranslations_id") })
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "articles_categories", joinColumns = { @JoinColumn(name = "articles_id") }, inverseJoinColumns = { @JoinColumn(name = "roles_id") })
    private Set<Category> categories = new HashSet<>();

}
