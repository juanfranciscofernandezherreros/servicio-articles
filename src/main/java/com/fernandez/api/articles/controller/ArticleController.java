package com.fernandez.api.articles.controller;

import com.fernandez.api.articles.constants.UriApi;
import com.fernandez.api.articles.dto.ArticleDto;
import com.fernandez.api.articles.service.ArticleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final @NotNull ArticleService articleService;

    @PostMapping(UriApi.PROTECTED + UriApi.V1 + UriApi.ARTICLES)
    public ArticleDto save(final ArticleDto articleDto) {
        log.info("[ArticleController][Create] articleDto={}", articleDto);
        return articleService.save(articleDto);
    }

    @PutMapping(UriApi.PROTECTED + UriApi.V1 + UriApi.ARTICLES)
    public ArticleDto update(final ArticleDto articleDto) {
        log.info("[ArticleController][update] articleDTO={}", articleDto);
        return articleService.update(articleDto);
    }

    @GetMapping(UriApi.PUBLIC + UriApi.V1 + UriApi.ARTICLES)
    public Page<ArticleDto> findAll(@RequestHeader("Accept-Language") final String acceptLanguage,
                                    @RequestParam(required = false) final String name,
                                    @RequestParam(required = false) final List<String> tags,
                                    @RequestParam(required = false) final List<String> categories,
                                    @PageableDefault(size = 5) final Pageable pageable) {
        log.info("[ArticleController][findAll] acceptLanguage={} name={} tags={} categories={} pageable={}", acceptLanguage, name, tags, categories, pageable);
        return articleService.findAllArticles(acceptLanguage, name, tags, categories, pageable);
    }

    @GetMapping(UriApi.PUBLIC + UriApi.V1)
    public ArticleDto findArticleBySlugOrId(@RequestParam(required = false) final Long articleId,
                                            @RequestParam(required = false) final String slug) {
        log.info("[ArticleController][findArticleBySlugOrId] articleId={} slug={}", articleId, slug);
        return articleService.findArticleBySlugOrId(slug, articleId);
    }

    @DeleteMapping(UriApi.PROTECTED + UriApi.V1)
    public void deleteById(@RequestParam final Long articleId) {
        log.info("[ArticleController][deleteById] articleId={}", articleId);
        articleService.deleteArticleById(articleId);
    }
}
