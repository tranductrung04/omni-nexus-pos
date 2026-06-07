package com.shadow.payload.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private String name;
    private Double totalAmount;
    private List<OrderItemDTO> items;
    private Long branchId;
    private Long cashierId;
    private Long customerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
