package com.shadow.payload.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderItemDTO {
    private Long id;
    private Integer quantity;
    private Double price;
    private Long productId;
    private Long orderId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
