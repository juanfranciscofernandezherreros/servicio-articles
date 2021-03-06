package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.common.Messages;
import com.fernandez.api.articles.constants.PropertiesConstant;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.TagDTO;
import com.fernandez.api.articles.exceptions.ArticlesLogicException;
import com.fernandez.api.articles.model.Tag;
import com.fernandez.api.articles.repository.TagsRepository;
import com.fernandez.api.articles.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import static com.fernandez.api.articles.service.impl.CategoryServiceImpl.getPage;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TagsServiceImpl implements TagService {

    private final TagsRepository tagsRepository;

    private final Messages messages;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<TagDTO> tagDTOList(final ArticleDTO articleDTO) {
        return articleDTO.getTags()
                .stream()
                .map(tag -> findTagByNameAndLanguage(tag, articleDTO.getLanguage()))
                .collect(Collectors.toList());
    }

    @Override
    public Tag findTagById(final Long tagsId) {
        return tagsRepository.findById(tagsId)
                .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.TAG_NOT_FOUND)));
    }

    @Override
    public TagDTO findTagDtoById(final Long tagId) {
        return modelMapper.map(tagsRepository.findById(tagId), TagDTO.class);
    }

    @Override
    public TagDTO save(final TagDTO tagDTO) {
        Tag tag = modelMapper.map(tagDTO,Tag.class);
        return modelMapper.map(tagsRepository.save(tag), TagDTO.class);
    }

    @Override
    public void deleteById(final Long tagId) {
        tagsRepository.delete(tagsRepository.findById(tagId)
                .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.CATEGORY_NOT_FOUND))));
    }

    @Override
    public Page<TagDTO> findAll(final String acceptLanguage, final Pageable pageable) {
        return tagsRepository.findAllByLanguage(acceptLanguage, pageable)
                .map(this::mapFromEntityToDto);
    }

    @Override
    public Page findAllTagsRandom(final String acceptLanguage, final Pageable pageable) {
        List<Tag> list = tagsRepository.findAllByLanguage(acceptLanguage);
        Collections.shuffle(list, new Random(System.nanoTime()));
        return convertList2Page(list,pageable);
    }

    private TagDTO mapFromEntityToDto(final Tag tag) {
        return modelMapper.map(tag,TagDTO.class);
    }

    private TagDTO findTagByNameAndLanguage(final TagDTO tagDTO, final String language) {
        ModelMapper modelMapper = new ModelMapper();
        Tag tag = tagsRepository.findByNameAndLanguage(tagDTO.getName(), language);
        if (Objects.nonNull(tag)) {
             modelMapper.map(tag, TagDTO.class);
        } else {
             modelMapper.map(tagsRepository.save(modelMapper.map(tagDTO, Tag.class)), TagDTO.class);
        }
        return tagDTO;
    }

    private Page convertList2Page(final List list, final Pageable pageable) {
        return getPage(list, pageable);
    }
}
