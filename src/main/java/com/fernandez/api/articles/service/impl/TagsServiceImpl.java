package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.common.Messages;
import com.fernandez.api.articles.constants.Properties;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.TagDto;
import com.fernandez.api.articles.exceptions.ArticlesLogicException;
import com.fernandez.api.articles.model.Tag;
import com.fernandez.api.articles.repository.TagsRepository;
import com.fernandez.api.articles.service.TagService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TagsServiceImpl implements TagService {

    private final TagsRepository tagsRepository;

    private final Messages messages;

    private ModelMapper modelMapper;

    private TagDto tagDto;

    private Tag tag;

    @Override
    public List<TagDto> tagDtoList(final ArticleDTO aricleDto) {
        return aricleDto.getTags()
                .stream()
                .map(tag -> findTagByNameAndLanguage(tag, aricleDto.getLanguage()))
                .collect(Collectors.toList());
    }

    @Override
    public Tag findTagById(final Long tagsId) {
        return tagsRepository.findById(tagsId)
                .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(Properties.TAG_NOT_FOUND)));
    }

    private TagDto findTagByNameAndLanguage(final TagDto tagDTO, final String language) {
        tag = tagsRepository.findByNameAndLanguage(tagDTO.getName(), language);
        if (Objects.nonNull(tag)) {
            tagDto = modelMapper.map(tag, TagDto.class);
        } else {
            tagDto = modelMapper.map(tagsRepository.save(modelMapper.map(tagDTO, Tag.class)), TagDto.class);
        }
        return tagDto;
    }
}
