package com.shadow.model;

import com.shadow.domain.PaymentType;
import lombok.Data;

@Data
public class PaymentSummary {
    private PaymentType paymentType;
    private Double totalAmount;
    private Integer transactionCount;
    private Double percentage;
}
