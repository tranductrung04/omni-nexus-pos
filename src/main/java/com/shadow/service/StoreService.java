package com.shadow.service;

import com.shadow.domain.StoreStatus;
import com.shadow.exception.StoreException;
import com.shadow.exception.UserException;
import com.shadow.model.Store;
import com.shadow.model.User;
import com.shadow.payload.dto.StoreDto;

import java.util.List;

public interface StoreService {
    StoreDto createStore(StoreDto storeDto, User user);

    StoreDto getStoreById(Long id) throws StoreException;

    Store getStoreEntityById(Long id) throws StoreException;

    List<StoreDto> getAllStores();

    Store getStoreByAdmin() throws UserException;

    StoreDto updateStore(Long id, StoreDto storeDto) throws StoreException, UserException;

    void deleteStore(Long id) throws UserException;

    StoreDto getStoreByEmployee() throws UserException;

    StoreDto moderateStore(Long id, StoreStatus status);
}
