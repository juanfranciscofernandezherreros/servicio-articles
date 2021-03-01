package com.fernandez.api.articles.service;

import com.fernandez.api.articles.dto.UserDto;

public interface UserService {
    UserDto findByUsername(String username);
}
