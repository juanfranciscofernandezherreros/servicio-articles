package com.fernandez.api.articles.controller;

import com.fernandez.api.articles.constants.UrlMapping;
import com.fernandez.api.articles.dto.CategoryDTO;
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

    @GetMapping(value = UrlMapping.PUBLIC + UrlMapping.TAGS)
    public Page<TagDTO> findAll(@RequestHeader("accept-language") final String acceptLanguage,
                                     @PageableDefault(page = 0, size = 5) Pageable pageable) {
        log.info("[TagsController][findAll] acceptLanguage={} tagId={}", acceptLanguage , pageable);
        return tagService.findAll(acceptLanguage,pageable);
    }

    @GetMapping(value = UrlMapping.PROTECTED + UrlMapping.TAGS)
    public TagDTO findById(@RequestParam final Long tagId) {
        log.info("[CategoryController][findById] tagId={}", tagId);
        return tagService.findTagDtoById(tagId);
    }

    @PostMapping(value = UrlMapping.PROTECTED + UrlMapping.TAGS)
    public TagDTO save(@Validated @RequestBody TagDTO tagDTO) {
        log.info("[TagsController][save] tagDTO={}", tagDTO);
        return tagService.save(tagDTO);
    }

    @PutMapping(value = UrlMapping.PROTECTED + UrlMapping.TAGS)
    public TagDTO update(@Validated @RequestBody TagDTO tagDTO) {
        log.info("[TagsController][update] tagDTO={}", tagDTO);
        return tagService.save(tagDTO);
    }

    @DeleteMapping(value = UrlMapping.PROTECTED + UrlMapping.TAGS)
    public void deleteTagById(@RequestParam final Long tagId) {
        log.info("[TagsController][deleteById] tagId={}", tagId);
        tagService.deleteById(tagId);
    }

}
