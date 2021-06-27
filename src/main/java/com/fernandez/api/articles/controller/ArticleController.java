package com.fernandez.api.articles.controller;

import com.fernandez.api.articles.constants.UrlMapping;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.CategoryDTO;
import com.fernandez.api.articles.dto.TagDTO;
import com.fernandez.api.articles.service.ArticleService;
import com.fernandez.api.articles.wrapper.ArticleWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = UrlMapping.ROOT, produces = {APPLICATION_JSON_VALUE})
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping(value = UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.ARTICLE)
    public ArticleDTO save(final @Valid @RequestBody ArticleDTO articleDTO) throws ParseException {
        log.info("[ArticleController][save] articleDTO={}", articleDTO);
        return articleService.save(articleDTO);
    }

    @PutMapping(value = UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.ARTICLE)
    public ArticleDTO update(final @RequestBody ArticleDTO articleDTO) throws ParseException {
        log.info("[ArticleController][update] articleDTO={}", articleDTO);
        return articleService.save(articleDTO);
    }

    @GetMapping(value = UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.ARTICLES)
    public Page<ArticleDTO> findAll(@RequestHeader("Accept-Language") final String acceptLanguage,
                                    final ArticleWrapper articleWrapper,
                                    @PageableDefault(size = 6) final Pageable pageable) {
        log.info("[ArticleController][findAll] acceptLanguage={} articleWrapper={} pageable={}", acceptLanguage , articleWrapper,pageable );
        return articleService.findAllArticles(acceptLanguage, articleWrapper, pageable);
    }

    @GetMapping(value = UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.ARTICLES + UrlMapping.RANDOM)
    public Page<ArticleDTO> findAllArticlesRandom(@RequestHeader("Accept-Language") final String acceptLanguage,
                                    @PageableDefault(size = 4) final Pageable pageable) {
        log.info("[ArticleController][findAll] acceptLanguage={} , pageable={}", acceptLanguage ,pageable );
        return articleService.findAllArticlesRandom(acceptLanguage, pageable);
    }

    @GetMapping(value = UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.ARTICLE)
    public ArticleDTO findArticleBySlugOrId(@RequestParam(required = false) final Long articleId,
                                            @RequestParam(required = false) final String slug) {
        log.info("[ArticleController][findArticleBySlugOrId] articleId={} slug={}", articleId , slug);
        return articleService.findArticleBySlugOrId(slug,articleId);
    }

    @GetMapping(value = UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.ARTICLE + UrlMapping.ARTICLE_ID + UrlMapping.CATEGORIES)
    public List<CategoryDTO> findCategoriesFromArticle(@RequestHeader("Accept-Language") final String acceptLanguage,@PathVariable final Long articleId) {
        log.info("[ArticleController][findCategoriesFromArticle] articleId={}", articleId);
        return articleService.findCategoriesFromArticle(acceptLanguage,articleId);
    }

    @GetMapping(value = UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.ARTICLE + UrlMapping.ARTICLE_ID + UrlMapping.TAGS)
    public List<TagDTO> findTagsFromArticle(@RequestHeader("Accept-Language") final String acceptLanguage, @PathVariable final Long articleId) {
        log.info("[ArticleController][findTagsFromArticle] articleId={}", articleId);
        return articleService.findTagsFromArticle(acceptLanguage,articleId);
    }

    @GetMapping(value = UrlMapping.PUBLIC + UrlMapping.V1 + "/playbyplay/article")
    public void addToArticle(
            @RequestParam String articleId,
            @RequestParam String numberofplay,
            @RequestParam String gamecode ,
            @RequestParam String seasoncode) throws IOException {
        log.info("[PlayByPlayController][addToArticle]");
        articleService.addPlayByPlay(articleId,numberofplay,gamecode,seasoncode);
    }

    @DeleteMapping(value = UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.ARTICLE)
    public void deleteArticleById(@RequestParam final Long id) {
        log.info("[ArticleController][deleteArticleById] id={}", id);
        articleService.deleteArticleById(id);
    }
}
