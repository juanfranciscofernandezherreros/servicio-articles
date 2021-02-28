package com.fernandez.api.articles.wrapper;

import lombok.Data;

import java.util.List;

@Data
public class ArticleWrapper {
    private final String title;
    private final String language;
    private final List<CategoryWrapper> categories;
    private final List<TagWrapper> tags;
}
