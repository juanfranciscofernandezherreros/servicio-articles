package com.fernandez.api.articles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private Long id;

    @NonNull
    @NotEmpty
    private String name;

    @NonNull
    @NotEmpty
    private String language;

    @NonNull
    @NotEmpty
    private String slug;

    private Long totalArticles;

    List<ArticleDTO> articles = new ArrayList<>();

    private boolean hasTag;

}
