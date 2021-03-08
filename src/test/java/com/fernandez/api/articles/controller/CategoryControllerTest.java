package com.fernandez.api.articles.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernandez.api.articles.constants.UrlMapping;
import com.fernandez.api.articles.service.CategoryService;
import com.fernandez.api.articles.util.CategoryDtoUtils;
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
import java.util.Locale;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CategoryController controller;

    @Mock
    private CategoryService service;

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
    public void saveCategoryTest() throws Exception {
        mockMvc.perform(post(UrlMapping.ROOT + UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.CATEGORY)
                .content(objectMapper.writeValueAsString(CategoryDtoUtils.mockCategroyDtoObject()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(service,times(1)).save(CategoryDtoUtils.mockCategroyDtoObject());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void updateCategoryTest() throws Exception {
        mockMvc.perform(put(UrlMapping.ROOT + UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.CATEGORY)
                .content(objectMapper.writeValueAsString(CategoryDtoUtils.mockCategroyDtoObject()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(service,times(1)).save(CategoryDtoUtils.mockCategroyDtoObject());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void deleteCategoryByIdTest() throws Exception {
        mockMvc.perform(delete(UrlMapping.ROOT + UrlMapping.PROTECTED + UrlMapping.V1 + UrlMapping.CATEGORY)
                .param("categoryId","1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(service,times(1)).deleteCategoryById(1L);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void findCategoryDtoByIdTest() throws Exception {
        mockMvc.perform(get(UrlMapping.ROOT + UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.CATEGORY)
                .param("categoryId","1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(service,times(1)).findCategoryDtoById(1L);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void findAllCategoriesTest() throws Exception {
        Pageable pageable = PageRequest.of(0, 5);
        mockMvc.perform(get(UrlMapping.ROOT + UrlMapping.PUBLIC + UrlMapping.V1 + UrlMapping.CATEGORIES)
                .header("Accept-Language","es-ES")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(service,times(1)).findAll("es-ES",pageable);
        verifyNoMoreInteractions(service);
    }
}
