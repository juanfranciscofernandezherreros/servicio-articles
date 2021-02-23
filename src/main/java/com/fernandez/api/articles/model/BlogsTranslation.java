package com.fernandez.api.articles.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
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
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blogstranslation")
public class BlogsTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String title;

    @Column(name = "descripcion")
    private String descripcion1;

    @Column(name = "content")
    private String content;

    @OneToOne
    @JoinColumn(name = "language_id", referencedColumnName = "id")
    private Language language;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String urlSeo;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
    @Column(name = "create_date")
    private String createDate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
    @Column(name = "update_date")
    private String updateDate;

    @ManyToOne
    @JoinColumn(name = "blogs_id")
    private Blogs blog;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "blogstranslation_tagstranslation", joinColumns = { @JoinColumn(name = "blogstranslation_id") }, inverseJoinColumns = { @JoinColumn(name = "tagstranslation_id") })
    private Set<TagsTranslation> tags = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "blogstranslation_categorytranslation", joinColumns = { @JoinColumn(name = "blogstranslation_id") }, inverseJoinColumns = { @JoinColumn(name = "categorytranslation_id") })
    private Set<CategoryTranslation> categories = new HashSet<>();

}
