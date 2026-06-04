package com.shadow.service;

import com.shadow.domain.StoreStatus;
import com.shadow.model.Store;
import com.shadow.payload.dto.StoreDTO;

import java.util.List;

public interface StoreService {
    StoreDTO createStore(StoreDTO storeDto);

    StoreDTO getStoreById(Long id);

    Store getStoreEntityById(Long id);

    List<StoreDTO> getAllStores();

    Store getStoreByAdmin();

    StoreDTO updateStore(Long id, StoreDTO storeDto);

    void deleteStore(Long id);

    StoreDTO getStoreByEmployee();

    StoreDTO moderateStore(Long id, StoreStatus status);
}
