package com.fernandez.api.articles.service;

import com.fernandez.api.articles.dto.ArticleDto;
import com.fernandez.api.articles.dto.TagDto;
import com.fernandez.api.articles.model.Tag;
import java.util.List;

public interface TagService {
    List<TagDto> tagDtoList(ArticleDto articleDto);

    Tag findTagById(Long valueOf);
}
