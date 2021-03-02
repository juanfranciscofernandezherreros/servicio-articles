package com.fernandez.api.articles.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class CategoryDTO {
    private Long id;
    private String name;
    private String language;
    private String slug;
    private Long totalArticles;
}
