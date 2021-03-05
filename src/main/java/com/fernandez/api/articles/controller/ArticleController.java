package com.fernandez.api.articles.controller;

import com.fernandez.api.articles.constants.UrlMapping;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        log.info("[ArticleController][save] articleDTO={}", articleDTO);
        return articleService.save(articleDTO);
    }

    @PutMapping(value = UrlMapping.PROTECTED + UrlMapping.ARTICLES)
    public ArticleDTO update(@RequestBody ArticleDTO articleDTO) {
        log.info("[ArticleController][update] articleDTO={}", articleDTO);
        return articleService.update(articleDTO);
    }

    @GetMapping(value = UrlMapping.PUBLIC + UrlMapping.ARTICLES)
    public Page<ArticleDTO> findAll(@RequestHeader("accept-language") String acceptLanguage,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) List<String> tags,
                                    @RequestParam(required = false) List<String> categories,
                                    @PageableDefault(size = 5) Pageable pageable) {
        log.info("[ArticleController][findAll] acceptLanguage={} name={} tags={} caregories={} pageable={}",
                acceptLanguage , name , tags , categories , pageable);
        return articleService.findAllArticles(acceptLanguage, name, tags, categories, pageable);
    }

    @GetMapping(value = UrlMapping.PUBLIC)
    public ArticleDTO findArticleBySlugOrId(@RequestParam(required = false) Long articleId,
                                            @RequestParam(required = false) String slug) {
        log.info("[ArticleController][findArticleBySlugOrId] articleId={} slug?{}" , articleId , slug);
        return articleService.findArticleBySlugOrId(slug,articleId);
    }

    @DeleteMapping(value = UrlMapping.PROTECTED + UrlMapping.ARTICLES)
    public void deleteById(@RequestParam Long id) {
        log.info("[ArticleController][deleteById] id={}" , id );
        articleService.deleteArticleById(id);
    }
}
