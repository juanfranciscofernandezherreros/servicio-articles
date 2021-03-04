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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TagsServiceImpl implements TagService {

    private final TagsRepository tagsRepository;

    private final Messages messages;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<TagDTO> tagDTOList(final ArticleDTO articleDTO) {
        return articleDTO.getTags()
                .stream()
                .map(tag -> findTagByNameAndLanguage(tag, articleDTO.getLanguage()))
                .collect(Collectors.toList());
    }

    @Override
    public Tag findTagById(Long tagsId) {
        return tagsRepository.findById(tagsId)
                .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.TAG_NOT_FOUND)));
    }

    @Override
    public TagDTO findTagDtoById(Long tagId) {
        return modelMapper.map(tagsRepository.findById(tagId), TagDTO.class);
    }

    @Override
    public TagDTO save(TagDTO tagDTO) {
        Tag tag = modelMapper.map(tagDTO,Tag.class);
        return modelMapper.map(tagsRepository.save(tag), TagDTO.class);
    }

    @Override
    public void deleteById(Long tagId) {
        tagsRepository.delete(tagsRepository.findById(tagId)
                .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.CATEGORY_NOT_FOUND))));
    }

    @Override
    public Page<TagDTO> findAll(String acceptLanguage, Pageable pageable) {
        return tagsRepository.findAllByLanguage(acceptLanguage, pageable)
                .map(tag -> mapFromEntityToDto(tag));
    }

    @Override
    public Page<TagDTO> findAllTagsRandom(String acceptLanguage, Pageable pageable) {
        List<Tag> list = tagsRepository.findAllByLanguage(acceptLanguage);
        Collections.shuffle(list, new Random(System.nanoTime()));
        return convertList2Page(list,pageable);
    }

    private TagDTO mapFromEntityToDto(Tag tag) {
        return modelMapper.map(tag,TagDTO.class);
    }

    private TagDTO findTagByNameAndLanguage(TagDTO tagDTO, final String language) {
        ModelMapper modelMapper = new ModelMapper();
        tagDTO.setLanguage(language);
        Tag tag = tagsRepository.findByNameAndLanguage(tagDTO.getName(), language);
        if (Objects.nonNull(tag)) {
            tagDTO = modelMapper.map(tag, TagDTO.class);
        } else {
            tagDTO = modelMapper.map(tagsRepository.save(modelMapper.map(tagDTO, Tag.class)), TagDTO.class);
        }
        return tagDTO;
    }

    private Page convertList2Page(List list, Pageable pageable) {
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
                : pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }
}
