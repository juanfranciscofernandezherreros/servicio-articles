package com.fernandez.api.articles.service;

import com.fernandez.api.articles.dto.UserDTO;

public interface UserService {
    UserDTO findByUsername ( String username );
}
