package com.fernandez.api.articles.controller;

import com.fernandez.api.articles.constants.UrlMapping;
import com.fernandez.api.articles.dto.CategoryDTO;
import com.fernandez.api.articles.service.CategoryService;
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
public class CategoryController {

    private final CategoryService service;

    @GetMapping(value = UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.CATEGORIES)
    public Page<CategoryDTO> findAll(@RequestHeader("Accept-Language") final String acceptLanguage,
                                     @PageableDefault(size = 5) final Pageable pageable) {
        log.info("[CategoryController][findAll] acceptLanguage={} pageable={}", acceptLanguage,pageable);
        return service.findAll(acceptLanguage,pageable);
    }

    @PostMapping(value = UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.CATEGORY)
    public CategoryDTO save(final @Validated @RequestBody CategoryDTO categoryDTO) {
        log.info("[CategoryController][save] categoryDTO={}", categoryDTO);
        return service.save(categoryDTO);
    }

    @PutMapping(value = UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.CATEGORY)
    public CategoryDTO update(final @Validated @RequestBody CategoryDTO categoryDTO) {
        log.info("[CategoryController][update] categoryDTO={}", categoryDTO);
        return service.save(categoryDTO);
    }

    @GetMapping(value = UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.CATEGORY)
    public CategoryDTO findCategoryBySlugOrId(@RequestParam(required = false) final Long categoryId,
                                                @RequestParam(required = false) final String slug) {
        log.info("[CategoryController][findById] categoryId={}", categoryId);
        return service.findCategoryBySlugOrId(categoryId,slug);
    }

    @DeleteMapping(value = UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.CATEGORY)
    public void deleteCategoryById(@RequestParam final Long categoryId) {
        log.info("[CategoryController][deleteById] categoryId={}", categoryId);
        service.deleteCategoryById(categoryId);
    }

}
