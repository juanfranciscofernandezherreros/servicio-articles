package com.fernandez.api.articles.service;

import com.fernandez.api.articles.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDTO findByUsername ( String username );
    Page<UserDTO> findAll (Pageable pageable );
}
