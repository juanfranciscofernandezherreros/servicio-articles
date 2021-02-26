package com.fernandez.api.articles.service;

import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.TagDTO;

import java.util.List;

public interface TagService {
    List<TagDTO> tagDTOList(ArticleDTO articleDTO);
}
