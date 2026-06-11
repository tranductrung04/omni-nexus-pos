package com.shadow.payload.dto;

import com.shadow.model.PaymentSummary;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ShiftReportDTO {
    private Long id;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private Double totalSales;
    private Double totalRefunds;
    private Integer totalOrders;
    private Double netSale;
    private List<PaymentSummary> paymentSummaries;
    private List<ProductDTO> topSellingProducts;
    private List<OrderDTO> recentOrders;
    private List<RefundDTO> refunds;
    private Long cashierId;
    private Long branchId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
