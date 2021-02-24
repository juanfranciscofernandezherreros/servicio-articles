package com.fernandez.api.articles.service;

import com.fernandez.api.articles.dto.UserDTO;
import com.fernandez.api.articles.model.User;

public interface UserService {
    UserDTO findByUsername(String username);
}
