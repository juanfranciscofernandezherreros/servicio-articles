package com.fernandez.api.articles.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArticleDTO {

    @NonNull
    String title;

    @NonNull
    String slug;

    @NonNull
    String description;

    @NonNull
    String content;

    @NonNull
    String mainImage;

    @NonNull
    String language;

    UserDTO user;

    List<TagDTO> tags = new ArrayList<>();

    List<CategoryDTO> categories = new ArrayList<>();

}
