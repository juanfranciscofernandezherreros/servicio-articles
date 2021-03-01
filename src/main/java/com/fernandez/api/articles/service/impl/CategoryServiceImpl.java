package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.common.Messages;
import com.fernandez.api.articles.constants.Properties;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.CategoryDTO;
import com.fernandez.api.articles.exceptions.ArticlesLogicException;
import com.fernandez.api.articles.model.Category;
import com.fernandez.api.articles.repository.CategoryRepository;
import com.fernandez.api.articles.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    private final Messages messages;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public CategoryDTO findByName(final String name) {
        return modelMapper.map(repository.findByName(name), CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> categoryDTOList(final ArticleDTO articleDTO) {
        final Type listType = new TypeToken<List<CategoryDTO>>() {}.getType();
        return modelMapper.map(
                articleDTO.getCategories()
                        .stream()
                        .map(categoryDTO -> findCategoryById(categoryDTO.getId()))
                        .collect(Collectors.toList()), listType);
    }

    @Override
    public Category findCategoryById(final Long categoryId) {
        return repository.findById(categoryId)
                .orElseThrow(() -> new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(Properties.CATEGORY_NOT_FOUND)));
    }

}
