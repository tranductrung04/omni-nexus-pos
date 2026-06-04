package com.shadow.service;

import com.shadow.payload.dto.UserDTO;
import com.shadow.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse signup(UserDTO userDto);

    AuthResponse login(UserDTO userDto);
}
