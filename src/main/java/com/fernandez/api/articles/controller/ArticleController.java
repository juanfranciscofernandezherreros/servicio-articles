package com.fernandez.api.articles.controller;

import com.fernandez.api.articles.constants.UrlMapping;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.service.ArticleService;
import com.fernandez.api.articles.wrapper.ArticleWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = UrlMapping.ROOT, produces = {APPLICATION_JSON_VALUE})
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping(value = UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.ARTICLE)
    public ArticleDTO save(final @Validated @RequestBody ArticleDTO articleDTO) {
        log.info("[ArticleController][Create] articleDTO={}", articleDTO);
        return articleService.save(articleDTO);
    }

    @PutMapping(value = UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.ARTICLE)
    public ArticleDTO update(final @RequestBody ArticleDTO articleDTO) {
        log.info("[ArticleController][update] articleDTO={}", articleDTO);
        return articleService.update(articleDTO);
    }

    @GetMapping(value = UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.ARTICLES)
    public Page<ArticleDTO> findAll(@RequestHeader("Accept-Language") final String acceptLanguage,
                                    final ArticleWrapper articleWrapper,
                                    @PageableDefault(size = 5) final Pageable pageable) {
        return articleService.findAllArticles(acceptLanguage, articleWrapper, pageable);
    }

    @GetMapping(value = UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.ARTICLE)
    public ArticleDTO findArticleBySlugOrId(@RequestParam(required = false) final Long articleId,
                                            @RequestParam(required = false) final String slug) {
        log.info("[ArticleController][findArticleBySlugOrId] articleId={} slug={}", articleId , slug);
        return articleService.findArticleBySlugOrId(slug,articleId);
    }

    @DeleteMapping(value = UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.ARTICLE)
    public void deleteArticleById(@RequestParam final Long id) {
        articleService.deleteArticleById(id);
    }
}
