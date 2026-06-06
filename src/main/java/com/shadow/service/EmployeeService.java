package com.shadow.service;

import com.shadow.domain.UserRole;
import com.shadow.model.User;
import com.shadow.payload.dto.UserDTO;

import java.util.List;

public interface EmployeeService {
    UserDTO createStoreEmployee(UserDTO userDTO, Long storeId);

    UserDTO createBranchEmployee(UserDTO userDTO, Long branchId);

    User updateEmployee(Long id, UserDTO userDTO);

    void deleteEmployee(Long id);

    List<UserDTO> findStoreEmployees(Long storeId, UserRole role);

    List<UserDTO> findBranchEmployees(Long branchId, UserRole role);

}
