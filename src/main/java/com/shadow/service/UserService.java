package com.shadow.service;

import com.shadow.model.User;

import java.util.List;

public interface UserService {
    User getUserFromJwtToken(String token);

    User getCurrentUser();

    User getUserByEmail(String email);

    User getUserById(Long id);

    List<User> getAllUsers();
}
