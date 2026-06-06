package com.shadow.payload.dto;

import com.shadow.domain.UserRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private UserRole role;
    private String password;
    private Long storeId;
    private Long branchId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
}
