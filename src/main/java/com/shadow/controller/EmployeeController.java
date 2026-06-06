package com.shadow.controller;

import com.shadow.domain.UserRole;
import com.shadow.model.User;
import com.shadow.payload.dto.UserDTO;
import com.shadow.payload.response.ApiResponse;
import com.shadow.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/store/{storeId}")
    public ResponseEntity<UserDTO> createStoreEmployee(
            @RequestBody UserDTO userDTO,
            @PathVariable Long storeId) {
        return ResponseEntity.ok(employeeService.createStoreEmployee(userDTO, storeId));
    }

    @PostMapping("/branch/{branchId}")
    public ResponseEntity<UserDTO> createBranchEmployee(
            @RequestBody UserDTO userDTO,
            @PathVariable Long branchId) {
        return ResponseEntity.ok(employeeService.createBranchEmployee(userDTO, branchId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateEmployee(
            @PathVariable Long id,
            @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteEmployee(
            @PathVariable Long id) {
        employeeService.deleteEmployee(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Employee deleted successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<UserDTO>> findStoreEmployees(
            @PathVariable Long storeId,
            @RequestParam(value = "role", required = false) UserRole role) {
        return ResponseEntity.ok(employeeService.findStoreEmployees(storeId, role));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<UserDTO>> findBranchEmployees(
            @PathVariable Long branchId,
            @RequestParam(value = "role", required = false) UserRole role) {
        return ResponseEntity.ok(employeeService.findBranchEmployees(branchId, role));
    }

}
