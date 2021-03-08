package com.fernandez.api.articles.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernandez.api.articles.service.TagService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
public class TagsControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private TagsController controller;

    @Mock
    private TagService service;

    private static final ObjectMapper objectMapper = new ObjectMapper();



}
