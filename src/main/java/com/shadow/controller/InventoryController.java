package com.shadow.controller;

import com.shadow.payload.dto.InventoryDTO;
import com.shadow.payload.response.ApiResponse;
import com.shadow.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventories")
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryDTO> createInventory(
            @RequestBody InventoryDTO inventoryDTO) {
        return ResponseEntity.ok(inventoryService.createInventory(inventoryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryDTO> updateInventory(
            @PathVariable Long id,
            @RequestBody InventoryDTO inventoryDTO) {
        return ResponseEntity.ok(inventoryService.updateInventory(id, inventoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteInventory(
            @PathVariable Long id) {
        inventoryService.deleteInventory(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Inventory deleted successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getInventoryById(
            @PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }

    @GetMapping("/branch/{branchId}/product/{productId}")
    public ResponseEntity<InventoryDTO> getInventoryByProductIdAndBrandId(
            @PathVariable Long branchId,
            @PathVariable Long productId) {
        return ResponseEntity.ok(
                inventoryService.getInventoryByProductIdAndBranchId(productId, branchId));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<InventoryDTO>> getAllInventoriesByBranchId(
            @PathVariable Long branchId) {
        return ResponseEntity.ok(inventoryService.getAllInventoriesByBranchId(branchId));
    }
}
