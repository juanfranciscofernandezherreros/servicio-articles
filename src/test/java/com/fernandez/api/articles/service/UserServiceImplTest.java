package com.fernandez.api.articles.service;

import com.fernandez.api.articles.repository.UserRepository;
import com.fernandez.api.articles.service.impl.UserServiceImpl;
import com.fernandez.api.articles.util.ArticleDtoUtils;
import com.fernandez.api.articles.util.UserDtoUtils;
import com.fernandez.api.articles.util.UserEntityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Test
    public void findUserByUsernameTest() {
        when(repository.findByUsername(Mockito.any())).thenReturn(UserEntityUtils.mockUserEntity());
        service.findByUsername("kfh1992@gmail.com");
        verify(repository,times(1)).findByUsername(Mockito.any());
        verifyNoMoreInteractions(repository);
    }

}
