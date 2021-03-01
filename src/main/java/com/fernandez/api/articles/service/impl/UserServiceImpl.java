package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.common.Messages;
import com.fernandez.api.articles.constants.Properties;
import com.fernandez.api.articles.dto.UserDTO;
import com.fernandez.api.articles.exceptions.ArticlesLogicException;
import com.fernandez.api.articles.model.User;
import com.fernandez.api.articles.repository.UserRepository;
import com.fernandez.api.articles.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final UserRepository userRepository;

    private final Messages messages;

    private ModelMapper modelMapper = new ModelMapper();

    private User user;
    @Override
    public UserDTO findByUsername(final String username) {
         user = userRepository.findByUsername(username);
        if (Objects.nonNull(user)) {
            return modelMapper.map(user, UserDTO.class);
        } else {
            throw new ArticlesLogicException(HttpStatus.NOT_FOUND, messages.get(Properties.USER_NOT_FOUND));
        }
    }
}
