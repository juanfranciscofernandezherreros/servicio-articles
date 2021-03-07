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

    private final CategoryService categoryService;

    @GetMapping(value = UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.CATEGORIES)
    public Page<CategoryDTO> findAll(@RequestHeader("Accept-Language") final String acceptLanguage,
                                     @PageableDefault(size = 5) final Pageable pageable) {
        return categoryService.findAll(acceptLanguage,pageable);
    }

    @PostMapping(value = UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.CATEGORY)
    public CategoryDTO save(final @Validated @RequestBody CategoryDTO categoryDTO) {
        log.info("[CategoryController][save] categoryDTO={}", categoryDTO);
        return categoryService.save(categoryDTO);
    }

    @PutMapping(value = UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.CATEGORY)
    public CategoryDTO update(final @Validated @RequestBody CategoryDTO categoryDTO) {
        log.info("[CategoryController][update] categoryDTO={}", categoryDTO);
        return categoryService.save(categoryDTO);
    }

    @GetMapping(value = UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.CATEGORY)
    public CategoryDTO findById(@RequestParam final Long categoryId) {
        log.info("[CategoryController][findById] categoryId={}", categoryId);
        return categoryService.findCategoryDtoById(categoryId);
    }

    @DeleteMapping(value = UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.CATEGORY)
    public void deleteCategoryById(@RequestParam final Long categoryId) {
        log.info("[CategoryController][deleteById] categoryId={}", categoryId);
        categoryService.deleteById(categoryId);
    }

}
