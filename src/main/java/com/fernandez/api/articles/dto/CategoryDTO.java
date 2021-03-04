package com.fernandez.api.articles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDTO {
    private Long id;
    private String name;
    private String language;
    private String slug;
    private Long totalArticles;
}
