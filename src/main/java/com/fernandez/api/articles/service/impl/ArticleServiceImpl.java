package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.common.Messages;
import com.fernandez.api.articles.constants.PropertiesConstant;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.exceptions.ArticlesLogicException;
import com.fernandez.api.articles.model.Article;
import com.fernandez.api.articles.repository.ArticleRepository;
import com.fernandez.api.articles.service.ArticleService;
import com.fernandez.api.articles.service.CategoryService;
import com.fernandez.api.articles.service.TagService;
import com.fernandez.api.articles.service.UserService;
import com.fernandez.api.articles.wrapper.ArticleWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final UserService userService;

    private final CategoryService categoryService;

    private final TagService tagService;

    private final Messages messages;

    @Override
    public ArticleDTO save(final ArticleDTO articleDTO) {
        log.debug("[ArticleServiceImpl][save] articleDTO={}", articleDTO);
        ModelMapper modelMapper = new ModelMapper();
        if (articleDTO.getCategories().size() > 0) {
            articleDTO.setUser(userService.findByUsername(articleDTO.getUser().getUsername()));
            articleDTO.setCategories(categoryService.categoryDTOList(articleDTO));
            if (articleDTO.getTags().size() > 0) {
                articleDTO.setTags(tagService.tagDTOList(articleDTO));
            }
            return modelMapper.map(articleRepository.save(modelMapper.map(articleDTO, Article.class)), ArticleDTO.class);
        } else {
            throw new ArticlesLogicException(HttpStatus.BAD_REQUEST, PropertiesConstant.MINIMUM_ONE_CATEGORY);
        }
    }

    @Override
    public ArticleDTO findArticleBySlug(String slug) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(
                articleRepository.findArticleBySlug(slug)
                        .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.ARTICLE_NOT_FOUND)))
                , ArticleDTO.class);
    }

    @Override
    public ArticleDTO findArticleById(Long articleId) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(
                articleRepository.findById(articleId)
                        .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.ARTICLE_NOT_FOUND)))
                , ArticleDTO.class);
    }

    @Override
    public void deleteArticleById(Long articleId) {
        articleRepository.delete(articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.ARTICLE_NOT_FOUND))));
    }

    @Override
    public ArticleDTO update(ArticleDTO articleDTO) {
        ModelMapper modelMapper = new ModelMapper();
        if (articleDTO.getCategories().size() > 0) {
            articleRepository.findById(articleDTO.getId())
                    .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.ARTICLE_NOT_FOUND)));
            return modelMapper.map(articleRepository.save(modelMapper.map(articleDTO, Article.class)),ArticleDTO.class);
        }else{
            throw new ArticlesLogicException(HttpStatus.BAD_REQUEST, PropertiesConstant.MINIMUM_ONE_CATEGORY);
        }
    }


}
