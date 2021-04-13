package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.common.Messages;
import com.fernandez.api.articles.constants.PropertiesConstant;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.CategoryDTO;
import com.fernandez.api.articles.dto.TagDTO;
import com.fernandez.api.articles.exceptions.ArticlesLogicException;
import com.fernandez.api.articles.model.Article;
import com.fernandez.api.articles.model.Tag;
import com.fernandez.api.articles.repository.TagsRepository;
import com.fernandez.api.articles.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.*;
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
    public TagDTO findTagDtoById(final Long tagId) {
        TagDTO tagDTO = new TagDTO();
        Tag tag = tagsRepository.findById(tagId).get();
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        tagDTO.setLanguage(tag.getLanguage());
        tagDTO.setSlug(tag.getSlug());
        tagDTO.setArticles(tag.getTags().stream().map(x->mapToArticle(x)).collect(Collectors.toList()));
        return tagDTO;
    }

    @Override
    public TagDTO findTagDtoBySlug(final String slug) {
        return modelMapper.map(tagsRepository.findBySlug(slug), TagDTO.class);
    }

    @Override
    public TagDTO update(final TagDTO tagDTO) {
        Tag tagUpdated = null;
        Optional<Tag> tag = tagsRepository.findById(tagDTO.getId());
        if(tag.isPresent()){
            Tag tagFounded = tag.get() ;
            tagFounded.setId(tagDTO.getId());
            tagFounded.setName(tagDTO.getName());
            tagFounded.setSlug(tagDTO.getSlug());
            tagFounded.setLanguage(tagDTO.getLanguage());
            tagUpdated = tagsRepository.save(tagFounded);
            return modelMapper.map(tagUpdated, TagDTO.class);
        }else{
            throw new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.TAG_NOT_FOUND));
        }
    }

    @Override
    public TagDTO save(final TagDTO tagDTO) {
        Tag tag = modelMapper.map(tagDTO,Tag.class);
        return modelMapper.map(tagsRepository.save(tag), TagDTO.class);
    }

    @Override
    public void deleteById(final Long tagId) {
        tagsRepository.delete(tagsRepository.findById(tagId)
                .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.TAG_NOT_FOUND))));
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

    @Override
    public Tag findTagById(Long tagId) {
        return tagsRepository.findById(tagId)
                .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.TAG_NOT_FOUND)));
    }

    @Override
    public TagDTO findTagBySlugOrId(Long tagId, String slug) {
        log.info("[CategoryServiceImpl][findCategoryBySlugOrId] slug={} tagId={} slug={}", tagId , slug);
        TagDTO tagDTO = null;
        if(Objects.nonNull(tagId)){
            tagDTO = findTagDtoById(tagId);
        }
        if(Objects.nonNull(slug)){
            tagDTO = findTagDtoBySlug(slug);
        }
        return tagDTO;
    }

    private TagDTO mapFromEntityToDto(final Tag tag) {
        TagDTO tagDTO =  modelMapper.map(tag,TagDTO.class);
        Long totalArticles = tagsRepository.countTotalArticlesFromTag(tag);
        if(totalArticles>0) {
            tagDTO.setTotalArticles(totalArticles);
        }else{
            tagDTO.setTotalArticles(0L);
        }
        return tagDTO;
    }


    private ArticleDTO mapToArticle(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        return articleDTO;
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
