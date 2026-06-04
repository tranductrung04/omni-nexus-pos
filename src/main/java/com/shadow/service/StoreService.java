package com.shadow.service;

import com.shadow.domain.StoreStatus;
import com.shadow.exception.StoreException;
import com.shadow.exception.UserException;
import com.shadow.model.Store;
import com.shadow.payload.dto.StoreDTO;

import java.util.List;

public interface StoreService {
    StoreDTO createStore(StoreDTO storeDto) throws UserException;

    StoreDTO getStoreById(Long id) throws StoreException;

    Store getStoreEntityById(Long id) throws StoreException;

    List<StoreDTO> getAllStores();

    Store getStoreByAdmin() throws UserException;

    StoreDTO updateStore(Long id, StoreDTO storeDto) throws StoreException, UserException;

    void deleteStore(Long id) throws UserException;

    StoreDTO getStoreByEmployee() throws UserException;

    StoreDTO moderateStore(Long id, StoreStatus status);
}
