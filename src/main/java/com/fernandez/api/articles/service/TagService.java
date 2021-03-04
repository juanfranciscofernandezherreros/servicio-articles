package com.fernandez.api.articles.service;

import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.TagDTO;
import com.fernandez.api.articles.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    List<TagDTO> tagDTOList(ArticleDTO articleDTO);

    Tag findTagById(Long valueOf);

    TagDTO findTagDtoById(Long tagId);

    TagDTO save(TagDTO tagDTO);

    void deleteById(Long tagId);

    Page<TagDTO> findAll(String acceptLanguage, Pageable pageable);

    Page<TagDTO> findAllTagsRandom(String acceptLanguage, Pageable pageable);
}
