package com.shadow.controller;

import com.shadow.payload.dto.BranchDTO;
import com.shadow.payload.response.ApiResponse;
import com.shadow.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/branches")
public class BranchController {
    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchDTO> createBranch(
            @RequestBody BranchDTO branchDTO) {
        return ResponseEntity.ok(branchService.createBranch(branchDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDTO> updateBranch(
            @PathVariable Long id,
            @RequestBody BranchDTO branchDTO) {
        return ResponseEntity.ok(branchService.updateBranch(id, branchDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBranch(
            @PathVariable Long id) {
        branchService.deleteBranch(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Branch deleted successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getBranchById(
            @PathVariable Long id) {
        return ResponseEntity.ok(branchService.getBranchById(id));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<BranchDTO>> getAllBranchesByStoreId(
            @PathVariable Long storeId) {
        return ResponseEntity.ok(branchService.getBranchByStoreId(storeId));
    }
}
