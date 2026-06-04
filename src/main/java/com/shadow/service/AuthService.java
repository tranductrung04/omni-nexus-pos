package com.shadow.service;

import com.shadow.exception.UserException;
import com.shadow.payload.dto.UserDTO;
import com.shadow.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse signup(UserDTO userDto) throws UserException;

    AuthResponse login(UserDTO userDto) throws UserException;
}
