package com.shadow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shadow.domain.PaymentType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Refund extends BaseEntity {
    private String reason;
    private Double amount;
    private PaymentType paymentType;

    @ManyToOne
    private Order order;

    @ManyToOne
    @JsonIgnore
    private ShiftReport shiftReport;

    @ManyToOne
    private User cashier;

    @ManyToOne
    private Branch branch;
}
