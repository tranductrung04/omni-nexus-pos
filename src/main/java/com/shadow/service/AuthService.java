package com.shadow.service;

import com.shadow.exception.UserException;
import com.shadow.payload.dto.UserDto;
import com.shadow.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse signup(UserDto userDto) throws UserException;

    AuthResponse login(UserDto userDto) throws UserException;
}
