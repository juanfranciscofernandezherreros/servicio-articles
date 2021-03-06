package com.fernandez.api.articles.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernandez.api.articles.constants.UrlMapping;
import com.fernandez.api.articles.dto.ArticleDTO;
import com.fernandez.api.articles.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void createArticleTest() throws Exception {
        mockMvc.perform(post(UrlMapping.ROOT + UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.ARTICLES)
                .content(objectMapper.writeValueAsString(mockCustomerObject()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(service,times(1)).save(mockCustomerObject());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void updateArticleTest() throws Exception {
        mockMvc.perform(put(UrlMapping.ROOT + UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.ARTICLES)
                .content(objectMapper.writeValueAsString(mockCustomerObject()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(service,times(1)).update(mockCustomerObject());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void findArticleByIdOrSlug() throws Exception {

        mockMvc.perform(get(UrlMapping.ROOT + UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.ARTICLES)
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
        mockMvc.perform(delete(UrlMapping.ROOT + UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.ARTICLES)
                .param("id","1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(service,times(1)).deleteArticleById(1L);
        verifyNoMoreInteractions(service);
    }

    private ArticleDTO mockCustomerObject() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setTitle("Title");
        articleDTO.setSlug("Slug");
        articleDTO.setDescription("Description");
        articleDTO.setContent("Content");
        articleDTO.setMainImage("Image");
        articleDTO.setLanguage("es-ES");
        return articleDTO;
    }

}
