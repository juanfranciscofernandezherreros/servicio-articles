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
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping ( value = UrlMapping.PUBLIC + UrlMapping.CATEGORIES )
    public Page < CategoryDTO > findAll ( final @RequestHeader ( "accept-language" ) String acceptLanguage ,
                                          final @PageableDefault ( size = 5 ) Pageable pageable ) {
        log.info ( "[CategoryController][findAll] acceptLanguage={} , pageable={}" , acceptLanguage , pageable );
        Page < CategoryDTO > categoryDtoPage = categoryService.findAll ( acceptLanguage , pageable );
        return categoryDtoPage;
    }

    @PostMapping ( value = UrlMapping.PROTECTED + UrlMapping.CATEGORIES )
    public CategoryDTO save ( final @Validated @RequestBody CategoryDTO categoryDTO ) {
        log.info ( "[CategoryController][save] categoryDTO={}" , categoryDTO );
        return categoryService.save ( categoryDTO );
    }

    @PutMapping ( value = UrlMapping.PROTECTED + UrlMapping.CATEGORIES )
    public CategoryDTO update ( final @Validated @RequestBody CategoryDTO categoryDTO ) {
        log.info ( "[CategoryController][update] categoryDTO={}" , categoryDTO );
        return categoryService.save ( categoryDTO );
    }

    @GetMapping ( value = UrlMapping.PROTECTED + UrlMapping.CATEGORIES )
    public CategoryDTO findById ( final @RequestParam Long categoryId ) {
        log.info ( "[CategoryController][findById] categoryId={}" , categoryId );
        return categoryService.findCategoryDtoById ( categoryId );
    }

    @DeleteMapping ( value = UrlMapping.PROTECTED + UrlMapping.CATEGORIES )
    public void deleteCategoryById ( final @RequestParam Long categoryId ) {
        log.info ( "[CategoryController][deleteById] categoryId={}" , categoryId );
        categoryService.deleteById ( categoryId );
    }

}
