package com.fernandez.api.articles.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class CategoryDTO {
    Long id;
    String name;
    String language;
    String slug;
}
