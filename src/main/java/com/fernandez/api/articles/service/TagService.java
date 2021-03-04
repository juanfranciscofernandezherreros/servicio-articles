package com.fernandez.api.articles.service;

import com.fernandez.api.articles.dto.ArticleDto;
import com.fernandez.api.articles.dto.TagDTO;
import com.fernandez.api.articles.model.Tag;
import java.util.List;

public interface TagService {
    List<TagDTO> tagDtoList(ArticleDto articleDto);

    Tag findTagById(Long valueOf);
}
