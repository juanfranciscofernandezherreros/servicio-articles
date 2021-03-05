package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.common.Messages;
import com.fernandez.api.articles.constants.PropertiesConstant;
import com.fernandez.api.articles.dto.UserDTO;
import com.fernandez.api.articles.exceptions.ArticlesLogicException;
import com.fernandez.api.articles.model.User;
import com.fernandez.api.articles.repository.UserRepository;
import com.fernandez.api.articles.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final @NotNull UserRepository userRepository;

    private final @NotNull Messages messages;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserDTO findByUsername(String username) {
        log.info("[UserServiceImpl][findByUsername] username={}" , username);
        final User user = userRepository.findByUsername(username);
        return modelMapper.map(user, UserDTO.class);
    }
}
