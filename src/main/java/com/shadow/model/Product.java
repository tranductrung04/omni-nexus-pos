package com.shadow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String sku;

    private String description;
    private Double mrp;
    private Double sellingPrice;
    private String brand;
    private String imageUrl;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Store store;
}
