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
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = UrlMapping.ROOT, produces = {APPLICATION_JSON_VALUE})
public class TagsController {

    private final TagService tagService;

    @PostMapping(UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.TAG)
    public TagDTO save(@Validated @RequestBody final TagDTO tagDTO) {
        log.info("[TagsController][save] tagDTO={}", tagDTO);
        return tagService.save(tagDTO);
    }

    @GetMapping(UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.TAGS)
    public Page<TagDTO> findAll(@RequestHeader("Accept-Language") final String acceptLanguage,
                                     @PageableDefault(size = 5) final Pageable pageable) {
        log.info("[TagsController][findAll] acceptLanguage={} tagId={}", acceptLanguage , pageable);
        return tagService.findAll(acceptLanguage,pageable);
    }

    @GetMapping(UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.TAGS + UrlMapping.RANDOM)
    public Page<TagDTO> findAllTagsRandom(@RequestHeader("Accept-Language") final String acceptLanguage,
                                @PageableDefault(size = 5) final Pageable pageable) {
        log.info("[TagsController][findAll] acceptLanguage={} tagId={}", acceptLanguage , pageable);
        return tagService.findAllTagsRandom(acceptLanguage,pageable);
    }

    @GetMapping(UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.TAG)
    public TagDTO findTagBySlugOrId(@RequestParam(required = false) final Long tagId,
                                    @RequestParam(required = false) final String slug) {
        log.info("[findTagBySlugOrId][findById] tagId={} slug={}" + tagId,slug);
        return tagService.findTagBySlugOrId(tagId,slug);
    }


    @PutMapping(UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.TAG)
    public TagDTO update(@Validated @RequestBody final TagDTO tagDTO) {
        log.info("[TagsController][update] tagDTO={}", tagDTO);
        return tagService.save(tagDTO);
    }

    @DeleteMapping(UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.TAG)
    public void deleteTagById(@RequestParam final Long tagId) {
        log.info("[TagsController][deleteById] tagId={}", tagId);
        tagService.deleteById(tagId);
    }

}
