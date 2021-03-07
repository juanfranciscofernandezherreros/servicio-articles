package com.fernandez.api.articles.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernandez.api.articles.constants.UrlMapping;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.dto.CategoryDTO;
import com.fernandez.api.articles.dto.UserDTO;
import com.fernandez.api.articles.service.ArticleService;
import com.fernandez.api.articles.util.ArticleDtoUtils;
import com.fernandez.api.articles.wrapper.ArticleWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ArticleControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ArticleController controller;

    @Mock
    private ArticleService service;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers(new ViewResolver() {
                    @Override
                    public View resolveViewName(String viewName, Locale locale) throws Exception {
                        return new MappingJackson2JsonView();
                    }
                })
                .build();
    }

    @Test
    public void saveArticleTest() throws Exception {
        mockMvc.perform(post(UrlMapping.ROOT + UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.ARTICLE)
                .content(objectMapper.writeValueAsString(ArticleDtoUtils.mockArticleDtoObject()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(service,times(1)).save(ArticleDtoUtils.mockArticleDtoObject());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void updateArticleTest() throws Exception {
        mockMvc.perform(put(UrlMapping.ROOT + UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.ARTICLE)
                .content(objectMapper.writeValueAsString(ArticleDtoUtils.mockArticleDtoObject()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(service,times(1)).save(ArticleDtoUtils.mockArticleDtoObject());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void findAllArticlesWitouthFiltersTest() throws Exception {
        Pageable pageable = PageRequest.of(0, 5);
        mockMvc.perform(get(UrlMapping.ROOT + UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.ARTICLES)
                .header("Accept-Language","es-ES")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(service,times(1)).findAllArticles("es-ES",mockArticleWrapper(),pageable);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void findArticleByIdOrSlugTest() throws Exception {
        mockMvc.perform(get(UrlMapping.ROOT + UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.ARTICLE)
                .param("articleId","1")
                .param("slug","slug-1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(service,times(1)).findArticleBySlugOrId("slug-1", 1L);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void deleteArticleByIdTest() throws Exception {
        mockMvc.perform(delete(UrlMapping.ROOT + UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.ARTICLE)
                .param("id","1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(service,times(1)).deleteArticleById(1L);
        verifyNoMoreInteractions(service);
    }

    private ArticleWrapper mockArticleWrapper(){
        ArticleWrapper articleWrapper = new ArticleWrapper();
        return articleWrapper;
    }



}
