package com.shadow.service.impl;

import com.shadow.exception.ResourceNotFoundException;
import com.shadow.mapper.InventoryMapper;
import com.shadow.model.Branch;
import com.shadow.model.Inventory;
import com.shadow.model.Product;
import com.shadow.payload.dto.InventoryDTO;
import com.shadow.repository.InventoryRepository;
import com.shadow.service.BranchService;
import com.shadow.service.InventoryService;
import com.shadow.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final BranchService branchService;
    private final ProductService productService;
    private final InventoryMapper inventoryMapper;

    @Override
    public InventoryDTO createInventory(InventoryDTO inventoryDTO) {
        Branch branch = branchService.getBranchEntityById(inventoryDTO.getBranchId());

        Product product = productService.getProductEntityById(inventoryDTO.getProductId());

        Inventory inventory = inventoryMapper.toEntity(inventoryDTO);
        inventory.setBranch(branch);
        inventory.setProduct(product);
        Inventory savedInventory = inventoryRepository.save(inventory);

        return inventoryMapper.toDTO(savedInventory);
    }

    @Override
    public InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Inventory", id)
        );

        inventory.setQuantity(inventoryDTO.getQuantity());
        Inventory savedInventory = inventoryRepository.save(inventory);

        return inventoryMapper.toDTO(savedInventory);
    }

    @Override
    public void deleteInventory(Long id) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Inventory", id)
        );

        inventoryRepository.delete(inventory);
    }

    @Override
    public InventoryDTO getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Inventory", id)
        );
        return inventoryMapper.toDTO(inventory);
    }

    @Override
    public InventoryDTO getInventoryByProductIdAndBranchId(Long productId, Long branchId) {
        Inventory inventory = inventoryRepository.findByProductIdAndBranchId(productId, branchId);
        return inventoryMapper.toDTO(inventory);
    }

    @Override
    public List<InventoryDTO> getAllInventoriesByBranchId(Long branchId) {
        List<Inventory> inventories = inventoryRepository.findByBranchId(branchId);
        return inventories.stream().map(inventoryMapper::toDTO).toList();
    }
}
