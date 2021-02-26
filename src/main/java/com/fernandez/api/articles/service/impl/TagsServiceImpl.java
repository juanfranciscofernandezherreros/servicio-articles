package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.CategoryDTO;
import com.fernandez.api.articles.dto.TagDTO;
import com.fernandez.api.articles.model.Article;
import com.fernandez.api.articles.model.Tag;
import com.fernandez.api.articles.repository.TagsRepository;
import com.fernandez.api.articles.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.core.metrics.StartupStep;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TagsServiceImpl implements TagService {

    private final TagsRepository tagsRepository;

    @Override
    public List<TagDTO> tagDTOList(ArticleDTO articleDTO) {
        return articleDTO.getTags()
                .stream()
                .map(tag->findTagByNameAndLanguage(tag,articleDTO.getLanguage()))
                .collect(Collectors.toList());
    }

    private TagDTO findTagByNameAndLanguage(TagDTO tagDTO , String language) {
        ModelMapper modelMapper = new ModelMapper();
        tagDTO.setLanguage(language);
        Tag tag = tagsRepository.findByNameAndLanguage(tagDTO.getName(),language);
        if(Objects.nonNull(tag)){
            tagDTO =  modelMapper.map(tag, TagDTO.class);
        }else{
            tagDTO =modelMapper.map(tagsRepository.save(modelMapper.map(tagDTO, Tag.class)), TagDTO.class);
        }
        return tagDTO;
    }
}
