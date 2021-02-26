package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.CategoryDTO;
import com.fernandez.api.articles.dto.UserDTO;
import com.fernandez.api.articles.exceptions.ArticlesLogicException;
import com.fernandez.api.articles.model.Article;
import com.fernandez.api.articles.model.Category;
import com.fernandez.api.articles.model.User;
import com.fernandez.api.articles.repository.ArticleRepository;
import com.fernandez.api.articles.repository.CategoryRepository;
import com.fernandez.api.articles.repository.UserRepository;
import com.fernandez.api.articles.service.ArticleService;
import com.fernandez.api.articles.service.CategoryService;
import com.fernandez.api.articles.service.TagService;
import com.fernandez.api.articles.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final UserService userService;

    private final CategoryService categoryService;

    private final TagService tagService;

    @Override
    public ArticleDTO save(final ArticleDTO articleDTO) {
        log.debug("[ArticleServiceImpl][save] articleDTO={}", articleDTO);
        ModelMapper modelMapper = new ModelMapper();
        if(articleDTO.getCategories().size()>0) {
            articleDTO.setUser(userService.findByUsername(articleDTO.getUser().getUsername()));
            articleDTO.setCategories(categoryService.categoryDTOList(articleDTO));
            if (articleDTO.getTags().size() > 0) {
                articleDTO.setTags(tagService.tagDTOList(articleDTO));
            }
            return modelMapper.map(articleRepository.save(modelMapper.map(articleDTO, Article.class)),ArticleDTO.class);
        }else{
            throw new ArticlesLogicException(HttpStatus.BAD_REQUEST,"Tiene que a ver mínimo una categoría");
        }
    }

}
