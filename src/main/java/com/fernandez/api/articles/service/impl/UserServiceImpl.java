package com.fernandez.api.articles.service.impl;

import com.fernandez.api.articles.dto.UserDTO;
import com.fernandez.api.articles.repository.UserRepository;
import com.fernandez.api.articles.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO findByUsername(String username) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userRepository.findByUsername(username),UserDTO.class);
    }
}
