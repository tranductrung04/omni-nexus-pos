package com.shadow.mapper;

import com.shadow.model.Inventory;
import com.shadow.payload.dto.InventoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(source = "product.id", target = "productId")
    InventoryDTO toDTO(Inventory inventory);

    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Inventory toEntity(InventoryDTO inventoryDTO);
}
