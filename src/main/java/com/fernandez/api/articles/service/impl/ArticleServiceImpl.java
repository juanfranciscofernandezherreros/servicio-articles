package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.common.Messages;
import com.fernandez.api.articles.constants.Properties;
import com.fernandez.api.articles.dto.ArticleDto;
import com.fernandez.api.articles.exceptions.ArticlesLogicException;
import com.fernandez.api.articles.model.Article;
import com.fernandez.api.articles.model.Category;
import com.fernandez.api.articles.model.Tag;
import com.fernandez.api.articles.repository.ArticleRepository;
import com.fernandez.api.articles.service.ArticleService;
import com.fernandez.api.articles.service.CategoryService;
import com.fernandez.api.articles.service.TagService;
import com.fernandez.api.articles.service.UserService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final @NotNull ArticleRepository articleRepository;

    private final @NotNull UserService userService;

    private final @NotNull CategoryService categoryService;

    private final @NotNull TagService tagService;

    private final @NotNull Messages messages;

    private final ModelMapper modelMapper = new ModelMapper();

    private ArticleDto articleDto;

    private Page<ArticleDto> articleList;

    @Override
    public ArticleDto save(final @NotNull ArticleDto articleDTO) {
        log.debug("[ArticleServiceImpl][save] articleDTO={}", articleDTO);
        if (checkCategoriesSize(articleDTO)) {
            articleDTO.setUser(userService.findByUsername(articleDTO.getUser().getUsername()));
            articleDTO.setCategories(categoryService.categoryDtoList(articleDTO));
            if (checkTagsSize(articleDTO)) {
                articleDTO.setTags(tagService.tagDtoList(articleDTO));
            }
            return modelMapper.map(articleRepository.save(modelMapper.map(articleDTO, Article.class)), ArticleDto.class);
        } else {
            throw new ArticlesLogicException(HttpStatus.BAD_REQUEST, Properties.ONE_CATEGORY);
        }
    }

    @Override
    public void deleteArticleById(final @NotNull Long articleId) {
        articleRepository.deleteById(modelMapper.map(findArticleById(articleId).getId(), Long.class));
    }

    @Override
    public ArticleDto update(final @NotNull ArticleDto articleDTO) {
        if (checkCategoriesSize(articleDTO)) {
            findArticleById(articleDTO.getId());
            return modelMapper.map(articleRepository.save(modelMapper.map(articleDTO, Article.class)), ArticleDto.class);
        } else {
            throw new ArticlesLogicException(HttpStatus.BAD_REQUEST, Properties.ONE_CATEGORY);
        }
    }

    @Override
    public Page<ArticleDto> findAllArticles(final String acceptLanguage,
                                            final String name,
                                            final @NotNull List<String> tag,
                                            final @NotNull List<String> categories,
                                            final Pageable pageable) {
        if (Objects.isNull(name) && Objects.isNull(categories) && Objects.isNull(tag)) {
            articleList = articleRepository.findAllByLanguage(acceptLanguage, pageable)
                    .map(article -> modelMapper.map(article, ArticleDto.class));
        }
        if (Objects.nonNull(name)) {
            articleList = articleRepository.findArticleByLanguageAndTitle(acceptLanguage, name, pageable)
                    .map(article -> modelMapper.map(article, ArticleDto.class));
        }
        if (Objects.nonNull(categories)) {
            articleList = articleRepository.findByCategoriesIn(findAllCategoriesById(categories), pageable)
                    .map(article -> modelMapper.map(article, ArticleDto.class));
        }
        if (Objects.nonNull(tag)) {
            articleList = articleRepository.findByTagsIn(findAllTagsById(tag), pageable)
                    .map(article -> modelMapper.map(article, ArticleDto.class));
        }
        return articleList;
    }

    @Override
    public ArticleDto findArticleBySlugOrId(final String slug, final @NotNull Long articleId) {
        if (Objects.nonNull(slug)) {
            articleDto = findArticleBySlug(slug);
        }
        if (Objects.nonNull(articleId)) {
            articleDto = findArticleById(articleId);
        }
        return articleDto;
    }

    private List<Category> findAllCategoriesById(final @NotNull List<String> categories) {
        return categories.stream()
                .map(category -> categoryService.findCategoryById(Long.valueOf(category)))
                .collect(Collectors.toList());
    }

    private List<Tag> findAllTagsById(final @NotNull List<String> tags) {
        return tags.stream()
                .map(tag -> tagService.findTagById(Long.valueOf(tag)))
                .collect(Collectors.toList());
    }

    private ArticleDto findArticleBySlug(final String slug) {
        return modelMapper.map(
                articleRepository.findArticleBySlug(slug)
                        .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(Properties.ARTICLE_NOT_FOUND))), ArticleDto.class);
    }

    private ArticleDto findArticleById(final @NotNull Long articleId) {
        return modelMapper.map(articleRepository.findById(articleId)
                        .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(Properties.ARTICLE_NOT_FOUND))),
                ArticleDto.class);
    }

    private boolean checkCategoriesSize(final @NotNull ArticleDto articleDTO){
        return articleDTO.getCategories().size() > 0;
    }

    private boolean checkTagsSize(final @NotNull ArticleDto articleDTO) {
        return articleDTO.getTags().size() > 0;
    }


}
