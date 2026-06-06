package com.shadow.model;

import com.shadow.domain.StoreStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Store extends BaseEntity {
    @Column(nullable = false)
    private String brand;

    private String description;
    private String storeType;

    private StoreStatus status;

    @Embedded
    private StoreContact contact = new StoreContact();

    @OneToOne
    private User storeAdmin;

    @Override
    protected void onCreate() {
        super.onCreate();
        status = StoreStatus.PENDING;
    }
}
