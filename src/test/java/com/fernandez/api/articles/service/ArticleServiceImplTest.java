package com.fernandez.api.articles.service;

import com.fernandez.api.articles.dto.TagDTO;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceImplTest {

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Mock
    private UserService userService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private TagService tagService;

    @Mock
    private ArticleRepository articleRepository;

    @Test
    public void saveArticleWitouthTagsTest() {
        when(userService.findByUsername(Mockito.any())).thenReturn(UserDtoUtils.mockUserDtoObject());
        when(articleRepository.save(Mockito.any())).thenReturn(ArticleDtoUtils.mockArticleObject());
        articleService.save(ArticleDtoUtils.mockArticleDtoObject());
        verify(userService,times(1)).findByUsername(Mockito.any());
        verify(articleRepository,times(1)).save(Mockito.any());
        verifyNoMoreInteractions(userService,articleRepository);
    }

    @Test
    public void saveArticleWithTagsTest() {
        List<TagDTO> tagsList = new ArrayList<TagDTO>();
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("Tag1");
        tagDTO.setLanguage("es-ES");
        tagDTO.setSlug("Tag1-Slug");
        tagsList.add(tagDTO);
        when(userService.findByUsername(Mockito.any())).thenReturn(UserDtoUtils.mockUserDtoObject());
        when(tagService.tagDTOList(Mockito.any())).thenReturn(tagsList);
        when(articleRepository.save(Mockito.any())).thenReturn(ArticleDtoUtils.mockArticleObject());
        articleService.save(ArticleDtoUtils.mockArticleDtoWithTagsObject());
        verify(userService,times(1)).findByUsername(Mockito.any());
        verify(articleRepository,times(1)).save(Mockito.any());
        verify(tagService,times(1)).tagDTOList(Mockito.any());
        verifyNoMoreInteractions(userService,articleRepository,tagService);
    }

    @Test
    public void findArticleBySlugTest() {
        when(articleRepository.findArticleBySlug(Mockito.any())).thenReturn(ArticleDtoUtils.mockArticleOptional());
        articleService.findArticleBySlugOrId("slug-1",null);
        verify(articleRepository,times(1)).findArticleBySlug(Mockito.any());
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    public void findArticleByIdTest() {
        when(articleRepository.findById(Mockito.any())).thenReturn(ArticleDtoUtils.mockArticleOptional());
        articleService.findArticleBySlugOrId(null,1L);
        verify(articleRepository,times(1)).findById(Mockito.any());
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    public void deleteArticleByIdTest() {
        when(articleRepository.findById(Mockito.any())).thenReturn(ArticleDtoUtils.mockArticleOptional());
        doNothing().when(articleRepository).delete(Mockito.any());
        articleService.deleteArticleById(1L);
        verify(articleRepository,times(1)).findById(Mockito.any());
        verify(articleRepository,times(1)).delete(Mockito.any());
        verifyNoMoreInteractions(articleRepository);
    }

}
