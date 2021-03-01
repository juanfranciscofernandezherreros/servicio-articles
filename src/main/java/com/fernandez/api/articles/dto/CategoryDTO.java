package com.fernandez.api.articles.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDTO {
    private Long id;
    private String name;
    private String language;
}
