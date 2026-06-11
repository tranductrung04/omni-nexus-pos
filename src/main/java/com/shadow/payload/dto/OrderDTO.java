package com.shadow.payload.dto;

import com.shadow.domain.OrderStatus;
import com.shadow.domain.PaymentType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private String name;
    private Double totalAmount;
    private PaymentType paymentType;
    private OrderStatus status;
    private List<OrderItemDTO> items;
    private Long branchId;
    private Long cashierId;
    private Long customerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
