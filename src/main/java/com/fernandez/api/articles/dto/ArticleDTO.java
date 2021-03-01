package com.fernandez.api.articles.dto;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.ToString;

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

    private UserDto user;

    private List<TagDto> tags = new ArrayList<>();

    private List<CategoryDto> categories = new ArrayList<>();

}
