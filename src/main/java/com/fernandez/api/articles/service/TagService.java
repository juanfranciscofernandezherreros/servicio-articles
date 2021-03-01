package com.fernandez.api.articles.service;

import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.TagDto;
import com.fernandez.api.articles.model.Tag;
import java.util.List;

public interface TagService {
    List<TagDto> tagDtoList(ArticleDTO articleDto);

    Tag findTagById(Long valueOf);
}
