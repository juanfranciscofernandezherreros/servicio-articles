package com.fernandez.api.articles.controller;

import com.fernandez.api.articles.constants.UrlMapping;
import com.fernandez.api.articles.dto.TagDTO;
import com.fernandez.api.articles.service.TagService;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping ( value = UrlMapping.ROOT, produces = { APPLICATION_JSON_VALUE } )
public class TagsController {

    private final TagService tagService;

    @GetMapping ( UrlMapping.PUBLIC +  UrlMapping.V1 + UrlMapping.TAGS )
    public Page < TagDTO > findAll ( final @RequestHeader ( "accept-language" ) String acceptLanguage ,
                                     final @PageableDefault ( size = 5 ) Pageable pageable ) {
        log.info ( "[TagsController][findAll] acceptLanguage={} tagId={}" , acceptLanguage , pageable );
        return tagService.findAll ( acceptLanguage , pageable );
    }

    @GetMapping ( UrlMapping.PUBLIC +  UrlMapping.V1 + UrlMapping.TAGS + UrlMapping.RANDOM )
    public Page < TagDTO > findAllTagsRandom ( final @RequestHeader ( "accept-language" ) String acceptLanguage ,
                                               final @PageableDefault ( size = 5 ) Pageable pageable ) {
        log.info ( "[TagsController][findAll] acceptLanguage={} tagId={}" , acceptLanguage , pageable );
        return tagService.findAllTagsRandom ( acceptLanguage , pageable );
    }

    @GetMapping ( UrlMapping.PROTECTED +  UrlMapping.V1 + UrlMapping.TAGS )
    public TagDTO findById ( final @RequestParam Long tagId ) {
        log.info ( "[CategoryController][findById] tagId={}" , tagId );
        return tagService.findTagDtoById ( tagId );
    }

    @PostMapping ( UrlMapping.PROTECTED +  UrlMapping.V1 + UrlMapping.TAGS )
    public TagDTO save ( final @Validated @RequestBody TagDTO tagDTO ) {
        log.info ( "[TagsController][save] tagDTO={}" , tagDTO );
        return tagService.save ( tagDTO );
    }

    @PutMapping ( UrlMapping.PROTECTED +  UrlMapping.V1 + UrlMapping.TAGS )
    public TagDTO update ( final @Validated @RequestBody TagDTO tagDTO ) {
        log.info ( "[TagsController][update] tagDTO={}" , tagDTO );
        return tagService.save ( tagDTO );
    }

    @DeleteMapping ( UrlMapping.PROTECTED +  UrlMapping.V1 + UrlMapping.TAGS )
    public void deleteTagById ( final @RequestParam Long tagId ) {
        log.info ( "[TagsController][deleteById] tagId={}" , tagId );
        tagService.deleteById ( tagId );
    }

}
