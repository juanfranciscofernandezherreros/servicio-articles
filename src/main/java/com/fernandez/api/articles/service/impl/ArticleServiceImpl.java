package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.common.Messages;
import com.fernandez.api.articles.constants.PropertiesConstant;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.CategoryDTO;
import com.fernandez.api.articles.exceptions.ArticlesLogicException;
import com.fernandez.api.articles.model.Article;
import com.fernandez.api.articles.model.Category;
import com.fernandez.api.articles.model.Tag;
import com.fernandez.api.articles.model.auditable.Audit;
import com.fernandez.api.articles.repository.ArticleRepository;
import com.fernandez.api.articles.repository.CountCommentsBlogRepository;
import com.fernandez.api.articles.service.ArticleService;
import com.fernandez.api.articles.service.CategoryService;
import com.fernandez.api.articles.service.TagService;
import com.fernandez.api.articles.service.UserService;
import com.fernandez.api.articles.wrapper.ArticleWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final UserService userService;

    private final CountCommentsBlogRepository countCommentsRepository;

    private final CategoryService categoryService;

    private final TagService tagService;

    private final Messages messages;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public ArticleDTO save(final ArticleDTO articleDTO) {
        log.info("[ArticleServiceImpl][save] articleDTO={}", articleDTO);
        articleDTO.setUser(userService.findByUsername(articleDTO.getUser().getUsername()));
        articleDTO.setCategories(categoryService.categoryDTOList(articleDTO));
        if (articleDTO.getTags().size() > 0) {
            articleDTO.setTags(tagService.tagDTOList(articleDTO));
        }
        Article article = modelMapper.map(articleDTO, Article.class);
        return modelMapper.map(articleRepository.save(article), ArticleDTO.class);
    }

    @Override
    public void deleteArticleById(final Long articleId) {
        log.info("[ArticleServiceImpl][deleteArticleById] articleId={}", articleId);
        articleRepository.delete(articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.ARTICLE_NOT_FOUND))));
    }

    @Override
    public Page<ArticleDTO> findAllArticles(final String acceptLanguage,
                                            final ArticleWrapper articleWrapper,
                                            final Pageable pageable) {
        log.info("[ArticleServiceImpl][findAllArticles] acceptLanguage={} articleWrapper={} pageable={} ", acceptLanguage , articleWrapper , pageable);
        List<Article> articles = new ArrayList<Article>();
        Page<ArticleDTO> articleList = null;
        if (Objects.isNull(articleWrapper.getName()) && Objects.isNull(articleWrapper.getCategories()) && Objects.isNull(articleWrapper.getTags())) {
            articleList = articleRepository.findAllByLanguage(acceptLanguage, pageable).map(this::mapFromEntityToDto);
        }
        if (Objects.nonNull(articleWrapper.getName())) {
            articleList = articleRepository.findArticleByLanguageAndTitle(acceptLanguage, articleWrapper.getName(), pageable)
                    .map(this::mapFromEntityToDto);
        }
        if (Objects.nonNull(articleWrapper.getCategories())) {
            articleList = articleRepository.findByCategoriesIn(findAllCategoriesById(articleWrapper.getCategories()), pageable)
                    .map(this::mapFromEntityToDto);
        }
        if (Objects.nonNull(articleWrapper.getTags())) {
            articleList = articleRepository.findByTagsIn(findAllTagsById(articleWrapper.getTags()), pageable)
                    .map(this::mapFromEntityToDto);
        }
        return articleList;
    }

    @Override
    public Page<ArticleDTO> findAllArticlesRandom(final String acceptLanguage, final Pageable pageable) {
        return articleRepository.findAllByLanguage(acceptLanguage,pageable).map(this::mapFromEntityToDto);
    }

    @Override
    public ArticleDTO findArticleBySlugOrId(final String slug, final Long articleId) {
        log.info("[ArticleServiceImpl][findArticleBySlugOrId] slug={} articleId={}", slug , articleId);
        ArticleDTO articleDTO = null;
        if(Objects.nonNull(slug)){
            articleDTO = findArticleBySlug(slug);
        }
        if(Objects.nonNull(articleId)){
            articleDTO = findArticleById(articleId);
        }
        return articleDTO;
    }

    private List<Category> findAllCategoriesById(final List<String> categories) {
        return categories.stream()
                .map(category -> categoryService.findCategoryById(Long.valueOf(category)))
                .collect(Collectors.toList());
    }

    private List<Tag> findAllTagsById(final List<String> tags) {
        return tags.stream()
                .map(tag -> tagService.findTagById(Long.valueOf(tag)))
                .collect(Collectors.toList());
    }


    private ArticleDTO findArticleBySlug(final String slug) {
        return modelMapper.map(articleRepository.findArticleBySlug(slug)
                        .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.ARTICLE_NOT_FOUND)))
                , ArticleDTO.class);
    }

    private ArticleDTO findArticleById(final Long articleId) {
        return modelMapper.map(
                articleRepository.findById(articleId)
                        .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.ARTICLE_NOT_FOUND)))
                , ArticleDTO.class);
    }

    private ArticleDTO mapFromEntityToDto(final Article article) {
        ArticleDTO articleDto = modelMapper.map(article, ArticleDTO.class);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = formatter.format(article.getAudit().getCreatedOn());
        articleDto.setCreatedDate(formattedDate);
        articleDto.setTotalComments(countCommentsRepository.countCommentsFromArticle(article.getId()));
        return articleDto;
    }
}
