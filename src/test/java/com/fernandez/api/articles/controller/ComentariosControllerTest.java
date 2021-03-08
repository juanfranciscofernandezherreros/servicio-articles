package com.fernandez.api.articles.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernandez.api.articles.constants.UrlMapping;
import com.fernandez.api.articles.dto.ComentariosDTO;
import com.fernandez.api.articles.service.ArticleService;
import com.fernandez.api.articles.service.ComentarioService;
import com.fernandez.api.articles.util.ArticleDtoUtils;
import com.fernandez.api.articles.util.CommentsDtoUtils;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ComentariosControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ComentariosController controller;

    @Mock
    private ComentarioService service;

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
    public void findAllCommentsFromArticlesTest() throws Exception {
        List<ComentariosDTO> comentariosDTOList = new ArrayList<ComentariosDTO>();
        mockMvc.perform(get(UrlMapping.ROOT + UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.COMMENTS)
                .param("articleId","1")
                .header("Accept-Language","es-ES")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(service,times(1)).findAllComentariosByBlogTranslationId(0L,0,1L,comentariosDTOList);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void saveCommentWithUserFoundTest() throws Exception {
        List<ComentariosDTO> comentariosDTOList = new ArrayList<ComentariosDTO>();
        mockMvc.perform(post(UrlMapping.ROOT + UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.COMMENT)
                .param("articleId","1")
                .content(objectMapper.writeValueAsString(CommentsDtoUtils.mockComentariosDtoObjectWithUserFound()))
                .header("Accept-Language","es-ES")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(service,times(1)).save(CommentsDtoUtils.mockComentariosDtoObjectWithUserFound());
        verifyNoMoreInteractions(service);
    }
}
