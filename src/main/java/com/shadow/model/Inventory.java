package com.shadow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory extends BaseEntity {
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private Product product;
}
