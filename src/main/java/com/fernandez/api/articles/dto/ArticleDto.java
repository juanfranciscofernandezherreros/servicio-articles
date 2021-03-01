package com.fernandez.api.articles.dto;

import org.jetbrains.annotations.NotNull;
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
public class ArticleDto {

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

    private @NotNull List<TagDto> tags = new ArrayList<>();

    private @NotNull List<CategoryDto> categories = new ArrayList<>();

}
