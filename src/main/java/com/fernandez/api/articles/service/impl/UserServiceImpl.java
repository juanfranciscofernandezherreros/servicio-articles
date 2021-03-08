package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.dto.UserDTO;
import com.fernandez.api.articles.model.User;
import com.fernandez.api.articles.repository.UserRepository;
import com.fernandez.api.articles.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final @NotNull UserRepository userRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserDTO findByUsername(final String username) {
        User user = userRepository.findByUsername(username);
        return modelMapper.map(user, UserDTO.class);
    }
}
