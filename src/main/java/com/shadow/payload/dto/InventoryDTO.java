package com.shadow.payload.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryDTO {
    private Long id;
    private Integer quantity;
    private Long branchId;
    private Long productId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
