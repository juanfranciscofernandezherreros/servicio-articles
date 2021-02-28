package com.fernandez.api.articles.wrapper;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class CategoryWrapper {
    Long id;
    String name;
}
