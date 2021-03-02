package com.fernandez.api.articles.controller;

import com.fernandez.api.articles.constants.UrlMapping;
import com.fernandez.api.articles.dto.CategoryDTO;
import com.fernandez.api.articles.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = UrlMapping.ROOT, produces = {APPLICATION_JSON_VALUE})
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value = UrlMapping.PUBLIC + UrlMapping.CATEGORIES)
    public Page<CategoryDTO> findAll(@RequestHeader("accept-language") final String acceptLanguage,
                                     @PageableDefault(page = 0, size = 5) Pageable pageable) {
        Page<CategoryDTO> categoryDtoPage = categoryService.findAll(acceptLanguage,pageable);
        return categoryDtoPage;
    }

}
