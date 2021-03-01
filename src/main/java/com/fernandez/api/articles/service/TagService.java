package com.fernandez.api.articles.service;

import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.TagDTO;
import com.fernandez.api.articles.model.Tag;

import java.util.List;

public interface TagService {
    List<TagDTO> tagDTOList(ArticleDTO articleDTO);

    Tag findTagById(Long valueOf);
}
