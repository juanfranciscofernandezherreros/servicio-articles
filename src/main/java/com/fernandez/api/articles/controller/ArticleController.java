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
@RequestMapping ( value = UrlMapping.ROOT, produces = { APPLICATION_JSON_VALUE } )
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping (UrlMapping.PROTECTED + UrlMapping.ARTICLES )
    public ArticleDTO save ( final @Validated @RequestBody ArticleDTO articleDTO ) {
        log.info ( "[ArticleController][save] articleDTO={}" , articleDTO );
        return articleService.save ( articleDTO );
    }

    @PutMapping (UrlMapping.PROTECTED + UrlMapping.ARTICLES )
    public ArticleDTO update ( final @RequestBody ArticleDTO articleDTO ) {
        log.info ( "[ArticleController][update] articleDTO={}" , articleDTO );
        return articleService.update ( articleDTO );
    }

    @GetMapping (UrlMapping.PUBLIC + UrlMapping.ARTICLES )
    public Page < ArticleDTO > findAll ( final @RequestHeader ( "accept-language" ) String acceptLanguage ,
                                         final @RequestParam ( required = false ) String name ,
                                         final @RequestParam ( required = false ) List < String > tags ,
                                         final @RequestParam ( required = false ) List < String > categories ,
                                         final @PageableDefault ( size = 5 ) Pageable pageable ) {
        log.info ( "[ArticleController][findAll] acceptLanguage={} name={} tags={} categories={} pageable={}" ,
                acceptLanguage , name , tags , categories , pageable );
        return articleService.findAllArticles ( acceptLanguage , name , tags , categories , pageable );
    }

    @GetMapping (UrlMapping.PUBLIC )
    public ArticleDTO findArticleBySlugOrId ( final @RequestParam ( required = false ) Long articleId ,
                                              final @RequestParam ( required = false ) String slug ) {
        log.info ( "[ArticleController][findArticleBySlugOrId] articleId={} slug?{}" , articleId , slug );
        return articleService.findArticleBySlugOrId ( slug , articleId );
    }

    @DeleteMapping (UrlMapping.PROTECTED + UrlMapping.ARTICLES )
    public void deleteById ( final @RequestParam Long id ) {
        log.info ( "[ArticleController][deleteById] id={}" , id );
        articleService.deleteArticleById ( id );
    }
}
