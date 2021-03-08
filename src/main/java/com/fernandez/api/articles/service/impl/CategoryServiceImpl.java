package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.common.Messages;
import com.fernandez.api.articles.constants.PropertiesConstant;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.CategoryDTO;
import com.fernandez.api.articles.exceptions.ArticlesLogicException;
import com.fernandez.api.articles.model.Category;
import com.fernandez.api.articles.repository.CategoryRepository;
import com.fernandez.api.articles.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final Messages messages;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<CategoryDTO> categoryDTOList(final ArticleDTO articleDTO) {
        Type listType = new TypeToken<List<CategoryDTO>>() {}.getType();
        return modelMapper.map(
                articleDTO.getCategories()
                        .stream()
                        .map(categoryDTO -> findCategoryById(categoryDTO.getId()))
                        .collect(Collectors.toList()), listType);
    }

    @Override
    public Page<CategoryDTO> findAll(final String acceptLanguage, final Pageable pageable) {
         return convertList2Page(categoryRepository.findAllByLanguage(acceptLanguage)
                 .stream()
                 .map(this::mapFromEntityToDto)
                 .sorted(Comparator.comparing(CategoryDTO::getTotalArticles).reversed())
                 .collect(Collectors.toList()),pageable);
    }

    @Override
    public CategoryDTO save(final CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO,Category.class);
        return modelMapper.map(categoryRepository.save(category),CategoryDTO.class);
    }

    @Override
    public void deleteCategoryById(final Long id) {
        categoryRepository.delete(categoryRepository.findById(id)
                .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.CATEGORY_NOT_FOUND))));
    }

    private CategoryDTO mapFromEntityToDto(final Category category) {
        CategoryDTO categoryDto = modelMapper.map(category,CategoryDTO.class);
        Long totalArticles = categoryRepository.countTotalArticlesFromCategory(category);
        if(totalArticles>0) {
            categoryDto.setTotalArticles(totalArticles);
        }else{
            categoryDto.setTotalArticles(0L);
        }
        return categoryDto;
    }

    @Override
    public Category findCategoryById(final Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.CATEGORY_NOT_FOUND)));
    }

    @Override
    public CategoryDTO findCategoryDtoById(final Long categoryDTO) {
        return modelMapper.map(findCategoryById(categoryDTO),CategoryDTO.class);
    }

    private Page convertList2Page(final List list, final Pageable pageable) {
        return getPage(list, pageable);
    }

    @NotNull
    static Page getPage(final List list, final Pageable pageable) {
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
                : pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

}
