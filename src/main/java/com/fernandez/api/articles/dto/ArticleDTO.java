package com.fernandez.api.articles.dto;

import lombok.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticleDTO {

    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String slug;

    @NonNull
    private String description;

    @NonNull
    private String content;

    @NonNull
    private String mainImage;

    @NonNull
    private String language;

    private UserDTO user;

    private List<TagDTO> tags = new ArrayList<>();

    private List<CategoryDTO> categories = new ArrayList<>();

}
