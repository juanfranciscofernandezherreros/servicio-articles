package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.UserDTO;
import com.fernandez.api.articles.model.Article;
import com.fernandez.api.articles.model.User;
import com.fernandez.api.articles.repository.ArticleRepository;
import com.fernandez.api.articles.repository.UserRepository;
import com.fernandez.api.articles.service.ArticleService;
import com.fernandez.api.articles.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final UserService userService;

    @Override
    public ArticleDTO save(final ArticleDTO articleDTO) {
        ModelMapper modelMapper = new ModelMapper();
        articleDTO.setUser(userService.findByUsername(articleDTO.getUser().getUsername()));
        return modelMapper.map(articleRepository.save(modelMapper.map(articleDTO, Article.class)),ArticleDTO.class);
    }

}
