package com.shadow.payload.response;

import com.shadow.payload.dto.UserDTO;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private UserDTO user;
}
