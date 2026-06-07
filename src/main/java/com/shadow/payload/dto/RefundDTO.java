package com.shadow.payload.dto;

import com.shadow.domain.PaymentType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RefundDTO {
    private Long id;
    private String reason;
    private Double amount;
    private PaymentType paymentType;
    private Long orderId;
    private Long shiftReportId;
    private Long cashierId;
    private Long branchId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
