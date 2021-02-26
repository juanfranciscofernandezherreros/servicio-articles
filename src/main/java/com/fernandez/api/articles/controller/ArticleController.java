package com.fernandez.api.articles.controller;

import com.fernandez.api.articles.constants.UrlMapping;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = UrlMapping.ROOT, produces = {APPLICATION_JSON_VALUE})
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping(value = UrlMapping.PROTECTED + UrlMapping.ARTICLES)
    public ArticleDTO save(@RequestHeader(value = "accept-language", required = true) final String iso2, @Validated @RequestBody final ArticleDTO articleDTO) {
        log.info("[ArticleController][Create] language={} articleDTO={}", iso2, articleDTO);
        articleDTO.setLanguage(iso2);
        return articleService.save(articleDTO);
    }
}
