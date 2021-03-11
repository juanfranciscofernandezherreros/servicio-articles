package com.fernandez.api.articles.util;

import com.fernandez.api.articles.model.User;

public class UserEntityUtils {
    public static User mockUserEntity() {
        User user = new User();
        user.setUsername("kfh1992");
        user.setEmail("kfh1992@gmail.com");
        user.setName("Name");
        user.setFirstName("FirstName");
        return user;
    }
}
