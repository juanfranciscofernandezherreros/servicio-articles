package com.fernandez.api.articles.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class ArticleDTO {

    Long id;

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
