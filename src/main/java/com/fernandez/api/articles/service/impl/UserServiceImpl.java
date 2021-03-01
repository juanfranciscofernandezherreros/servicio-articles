package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.common.Messages;
import com.fernandez.api.articles.constants.Properties;
import com.fernandez.api.articles.dto.UserDto;
import com.fernandez.api.articles.exceptions.ArticlesLogicException;
import com.fernandez.api.articles.model.User;
import com.fernandez.api.articles.repository.UserRepository;
import com.fernandez.api.articles.service.UserService;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final @NotNull UserRepository userRepository;

    private final @NotNull Messages messages;

    private final ModelMapper modelMapper = new ModelMapper();

    private User user;

    @Override
    public UserDto findByUsername(final String username) {
         user = userRepository.findByUsername(username);
        if (Objects.nonNull(user)) {
            return modelMapper.map(user, UserDto.class);
        } else {
            throw new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(Properties.USER_NOT_FOUND));
        }
    }
}
