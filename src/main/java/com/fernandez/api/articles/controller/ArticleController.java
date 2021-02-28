package com.fernandez.api.articles.controller;

import com.fernandez.api.articles.constants.UrlMapping;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.service.ArticleService;
import com.fernandez.api.articles.wrapper.ArticleWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping(value = UrlMapping.PROTECTED + UrlMapping.ARTICLES)
    public ArticleDTO save(@Validated @RequestBody ArticleDTO articleDTO) {
        log.info("[ArticleController][Create] articleDTO={}", articleDTO);
        return articleService.save(articleDTO);
    }

    @PutMapping(value = UrlMapping.PROTECTED + UrlMapping.ARTICLES)
    public ArticleDTO update(@RequestBody ArticleDTO articleDTO) {
        log.info("[ArticleController][update] articleDTO={}", articleDTO);
        return articleService.update(articleDTO);
    }

    @GetMapping(value = UrlMapping.PUBLIC + UrlMapping.SLUG)
    public ArticleDTO findArticleBySlug(@PathVariable final String slug) {
        return articleService.findArticleBySlug(slug);
    }

    @GetMapping(value = UrlMapping.PUBLIC)
    public ArticleDTO findArticleById(@RequestHeader(value = "accept-language", required = true) final String iso2 ,
                                      @RequestParam final Long id) {
        return articleService.findArticleById(id);
    }

    @DeleteMapping(value = UrlMapping.PROTECTED)
    public void deleteById(@RequestParam final Long id) {
        articleService.deleteArticleById(id);
    }
}
