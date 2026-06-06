package com.shadow.service;

import com.shadow.payload.dto.InventoryDTO;

import java.util.List;

public interface InventoryService {
    InventoryDTO createInventory(InventoryDTO inventoryDTO);

    InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO);

    void deleteInventory(Long id);

    InventoryDTO getInventoryById(Long id);

    InventoryDTO getInventoryByProductIdAndBranchId(Long productId, Long branchId);

    List<InventoryDTO> getAllInventoriesByBranchId(Long branchId);
}
