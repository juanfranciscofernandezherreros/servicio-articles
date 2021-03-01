package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.common.Messages;
import com.fernandez.api.articles.constants.Properties;
import com.fernandez.api.articles.dto.ArticleDto;
import com.fernandez.api.articles.dto.CategoryDto;
import com.fernandez.api.articles.exceptions.ArticlesLogicException;
import com.fernandez.api.articles.model.Category;
import com.fernandez.api.articles.repository.CategoryRepository;
import com.fernandez.api.articles.service.CategoryService;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final @NotNull CategoryRepository repository;

    private final @NotNull Messages messages;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public CategoryDto findByName(final String name) {
        return modelMapper.map(repository.findByName(name), CategoryDto.class);
    }

    @Override
    public List<CategoryDto> categoryDtoList(final @NotNull ArticleDto articleDto) {
        final Type listType = new TypeToken<List<CategoryDto>>() {}.getType();
        return modelMapper.map(
                articleDto.getCategories()
                        .stream()
                        .map(categoryDTO -> findCategoryById(categoryDTO.getId()))
                        .collect(Collectors.toList()), listType);
    }

    @Override
    public Category findCategoryById(final @NotNull Long categoryId) {
        return repository.findById(categoryId)
                .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(Properties.CATEGORY_NOT_FOUND)));
    }

}
