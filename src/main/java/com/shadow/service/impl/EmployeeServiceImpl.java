package com.shadow.service.impl;

import com.shadow.domain.UserRole;
import com.shadow.exception.ResourceNotFoundException;
import com.shadow.mapper.UserMapper;
import com.shadow.model.Branch;
import com.shadow.model.Store;
import com.shadow.model.User;
import com.shadow.payload.dto.UserDTO;
import com.shadow.repository.BranchRepository;
import com.shadow.repository.UserRepository;
import com.shadow.service.BranchService;
import com.shadow.service.EmployeeService;
import com.shadow.service.StoreService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final UserRepository userRepository;
    private final StoreService storeService;
    private final BranchService branchService;
    private final BranchRepository branchRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createStoreEmployee(UserDTO userDTO, Long storeId) {
        Store store = storeService.getStoreEntityById(storeId);

        Branch branch = null;
        if (userDTO.getRole() == UserRole.ROLE_BRANCH_MANAGER) {
            if (userDTO.getBranchId() == null) {
                throw new ValidationException("Branch id is required to create branch manager");
            }
            branch = branchService.getBranchEntityById(userDTO.getBranchId());
        }

        User user = userMapper.toEntity(userDTO);
        user.setStore(store);
        user.setBranch(branch);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User savedEmployee = userRepository.save(user);

        if (branch != null && userDTO.getRole() == UserRole.ROLE_BRANCH_MANAGER) {
            branch.setManager(user);
            branchRepository.save(branch);
        }

        return userMapper.toDTO(savedEmployee);
    }

    @Override
    public UserDTO createBranchEmployee(UserDTO userDTO, Long branchId) {
        Branch branch = branchService.getBranchEntityById(branchId);

        if (userDTO.getRole() == UserRole.ROLE_BRANCH_CASHIER
                || userDTO.getRole() == UserRole.ROLE_BRANCH_MANAGER) {
            User user = userMapper.toEntity(userDTO);
            user.setBranch(branch);
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            return userMapper.toDTO(userRepository.save(user));
        }

        throw new ValidationException("Branch role not supported");
    }

    @Override
    public User updateEmployee(Long id, UserDTO userDTO) {
        User employee = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", id)
        );

        Branch branch = branchService.getBranchEntityById(userDTO.getBranchId());

        employee.setEmail(userDTO.getEmail());
        employee.setFullName(userDTO.getFullName());
        employee.setPassword(userDTO.getPassword());
        employee.setRole(userDTO.getRole());
        employee.setBranch(branch);

        return userRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", id)
        );

        userRepository.delete(user);
    }

    @Override
    public List<UserDTO> findStoreEmployees(Long storeId, UserRole role) {
        Store store = storeService.getStoreEntityById(storeId);
        return userRepository.findByStoreId(storeId)
                .stream().filter(
                        user -> role == null || user.getRole() == role
                ).map(userMapper::toDTO).toList();
    }

    @Override
    public List<UserDTO> findBranchEmployees(Long branchId, UserRole role) {
        Branch branch = branchService.getBranchEntityById(branchId);
        return userRepository.findByBranchId(branchId)
                .stream().filter(
                        user -> role == null || user.getRole() == role
                ).map(userMapper::toDTO).toList();
    }
}
