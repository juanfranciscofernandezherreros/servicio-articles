package com.fernandez.api.articles.util;

import com.fernandez.api.articles.dto.UserDTO;

public class UserDtoUtils {
    public static UserDTO mockUserDtoObject() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("kfh1992");
        userDTO.setEmail("kfh1992@gmail.com");
        userDTO.setName("Name");
        userDTO.setFirstName("FirstName");
        return userDTO;
    }

}
