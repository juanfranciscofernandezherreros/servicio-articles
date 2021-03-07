package com.fernandez.api.articles.wrapper;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticleWrapper {

    private String name;

    private List<String> tags;

    private List<String> categories;

}
