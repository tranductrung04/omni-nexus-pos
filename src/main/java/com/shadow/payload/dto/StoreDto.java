package com.shadow.payload.dto;

import com.shadow.domain.StoreStatus;
import com.shadow.model.StoreContact;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreDto {
    private Long id;
    private String brand;
    private Long storeAdminId;
    private String description;
    private String storeType;
    private StoreStatus status;
    private StoreContact contact;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreated() {
        createdAt = LocalDateTime.now();
        status = StoreStatus.PENDING;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
