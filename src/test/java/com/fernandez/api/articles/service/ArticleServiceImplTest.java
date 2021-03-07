package com.fernandez.api.articles.service;

import com.fernandez.api.articles.repository.ArticleRepository;
import com.fernandez.api.articles.service.impl.ArticleServiceImpl;
import com.fernandez.api.articles.util.ArticleDtoUtils;
import com.fernandez.api.articles.util.UserDtoUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceImplTest {

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Mock
    private UserService userService;

    @Mock
    private ArticleRepository articleRepository;

    @Test
    public void saveArticleServiceTest() {
        when(userService.findByUsername(Mockito.any())).thenReturn(UserDtoUtils.mockUserDtoObject());
        when(articleRepository.save(Mockito.any())).thenReturn(ArticleDtoUtils.mockArticleObject());
        articleService.save(ArticleDtoUtils.mockArticleDtoObject());
        verify(userService,times(1)).findByUsername(Mockito.any());
        verify(articleRepository,times(1)).save(Mockito.any());
        verifyNoMoreInteractions(userService,articleRepository);
    }

}
