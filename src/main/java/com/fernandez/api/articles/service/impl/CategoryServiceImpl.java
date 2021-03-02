package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.common.Messages;
import com.fernandez.api.articles.constants.PropertiesConstant;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.CategoryDTO;
import com.fernandez.api.articles.exceptions.ArticlesLogicException;
import com.fernandez.api.articles.model.Category;
import com.fernandez.api.articles.repository.CategoryRepository;
import com.fernandez.api.articles.repository.CountCommentsBlogRepository;
import com.fernandez.api.articles.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public CategoryDTO findByName(final String name) {
        return modelMapper.map(categoryRepository.findByName(name), CategoryDTO.class);
    }

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
    public Page<CategoryDTO> findAll(String acceptLanguage, Pageable pageable) {
         return convertList2Page(categoryRepository.findAllByLanguage(acceptLanguage)
                 .stream()
                 .map(category -> mapFromEntityToDto(category))
                 .sorted(Comparator.comparing(CategoryDTO::getTotalArticles).reversed())
                 .collect(Collectors.toList()),pageable);
    }

    private CategoryDTO mapFromEntityToDto(Category category) {
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
    public Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(PropertiesConstant.CATEGORY_NOT_FOUND)));
    }

    private Page convertList2Page(List list, Pageable pageable) {
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
                : pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

}
