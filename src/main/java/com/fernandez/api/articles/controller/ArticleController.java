package com.fernandez.api.articles.controller;

import com.fernandez.api.articles.constants.UriApi;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping(UriApi.PROTECTED + UriApi.V1 + UriApi.ARTICLES)
    public ArticleDTO save(final ArticleDTO articleDTO) {
        log.info("[ArticleController][Create] articleDTO={}", articleDTO);
        return articleService.save(articleDTO);
    }

    @PutMapping(UriApi.PROTECTED + UriApi.V1 + UriApi.ARTICLES)
    public ArticleDTO update(final ArticleDTO articleDTO) {
        log.info("[ArticleController][update] articleDTO={}", articleDTO);
        return articleService.update(articleDTO);
    }

    @GetMapping(UriApi.PUBLIC + UriApi.V1 + UriApi.ARTICLES)
    public Page<ArticleDTO> findAll(@RequestHeader("Accept-Language") final String acceptLanguage,
                                    @RequestParam(required = false) final String name,
                                    @RequestParam(required = false) final List<String> tags,
                                    @RequestParam(required = false) final List<String> categories,
                                    @PageableDefault(size = 5) final Pageable pageable) {
        log.info("[ArticleController][findAll] acceptLanguage={} name={} tags={} categories={} pageable={}", acceptLanguage, name, tags, categories, pageable);
        return articleService.findAllArticles(acceptLanguage, name, tags, categories, pageable);
    }

    @GetMapping(UriApi.PUBLIC + UriApi.V1)
    public ArticleDTO findArticleBySlugOrId(@RequestParam(required = false) final Long articleId,
                                            @RequestParam(required = false) final String slug) {
        log.info("[ArticleController][findArticleBySlugOrId] articleId={} slug={}", articleId, slug);
        return articleService.findArticleBySlugOrId(slug, articleId);
    }

    @DeleteMapping(UriApi.PROTECTED + UriApi.V1)
    public void deleteById(@RequestParam final Long id) {
        log.info("[ArticleController][deleteById] id={}", id);
        articleService.deleteArticleById(id);
    }
}
