package com.shadow.service;

import com.shadow.exception.UserException;
import com.shadow.model.User;

import java.util.List;

public interface UserService {
    User getUserFromJwtToken(String token) throws UserException;

    User getCurrentUser() throws UserException;

    User getUserByEmail(String email) throws UserException;

    User getUserById(Long id) throws UserException;

    List<User> getAllUsers();
}
