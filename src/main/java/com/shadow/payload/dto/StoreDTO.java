package com.shadow.payload.dto;

import com.shadow.domain.StoreStatus;
import com.shadow.model.StoreContact;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreDTO {
    private Long id;
    private String brand;
    private Long storeAdminId;
    private String description;
    private String storeType;
    private StoreStatus status;
    private StoreContact contact;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
