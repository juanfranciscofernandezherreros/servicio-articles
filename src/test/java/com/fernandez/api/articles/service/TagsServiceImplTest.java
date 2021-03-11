package com.fernandez.api.articles.service;

import com.fernandez.api.articles.repository.TagsRepository;
import com.fernandez.api.articles.service.impl.TagsServiceImpl;
import com.fernandez.api.articles.util.TagEntityUtils;
import com.fernandez.api.articles.util.UserEntityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class TagsServiceImplTest {

    @InjectMocks
    private TagsServiceImpl service;

    @Mock
    private TagsRepository repository;

    @Test
    public void deleteByIdTest() {
        when(repository.findById(Mockito.any())).thenReturn(TagEntityUtils.mockTagEntity());
        doNothing().when(repository).delete(Mockito.any());
        service.deleteById(Mockito.any());
        verify(repository,times(1)).findById(Mockito.any());
        verifyNoMoreInteractions(repository);
    }
}
