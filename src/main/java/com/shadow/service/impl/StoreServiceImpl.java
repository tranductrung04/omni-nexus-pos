package com.shadow.service.impl;

import com.shadow.domain.StoreStatus;
import com.shadow.exception.DuplicateResourceException;
import com.shadow.exception.ForbiddenException;
import com.shadow.exception.ResourceNotFoundException;
import com.shadow.mapper.StoreMapper;
import com.shadow.model.Store;
import com.shadow.model.StoreContact;
import com.shadow.model.User;
import com.shadow.payload.dto.StoreDTO;
import com.shadow.repository.StoreRepository;
import com.shadow.service.StoreService;
import com.shadow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final UserService userService;
    private final StoreMapper storeMapper;

    @Override
    public StoreDTO createStore(StoreDTO storeDto) {
        User currentUser = userService.getCurrentUser();

        Store existingStore = storeRepository.findByStoreAdminId(currentUser.getId());
        if (existingStore != null) {
            throw new DuplicateResourceException("This admin already owns a store. Cannot create more");
        }

        Store store = storeMapper.toEntity(storeDto);
        store.setStoreAdmin(currentUser);

        return storeMapper.toDTO(storeRepository.save(store));
    }

    @Override
    public StoreDTO getStoreById(Long id) {
        Store store = storeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Store", id)
        );
        return storeMapper.toDTO(store);
    }

    @Override
    public Store getStoreEntityById(Long id) {
        return storeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Store", id)
        );
    }

    @Override
    public List<StoreDTO> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream().map(storeMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Store getStoreByAdmin() {
        User currentUser = userService.getCurrentUser();
        return storeRepository.findByStoreAdminId(currentUser.getId());
    }

    @Override
    public StoreDTO updateStore(Long id, StoreDTO storeDto) {
        Store existing = storeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Store", id)
        );

        existing.setBrand(storeDto.getBrand());
        existing.setDescription(storeDto.getDescription());

        if (storeDto.getStoreType() != null) {
            existing.setStoreType(storeDto.getStoreType());
        }

        if (storeDto.getContact() != null) {
            StoreContact contact = StoreContact.builder()
                    .address(storeDto.getContact().getAddress())
                    .phone(storeDto.getContact().getPhone())
                    .email(storeDto.getContact().getEmail())
                    .build();
            existing.setContact(contact);
        }
        return storeMapper.toDTO(storeRepository.save(existing));
    }

    @Override
    public void deleteStore(Long id) {
        Store store = getStoreByAdmin();
        storeRepository.delete(store);
    }

    @Override
    public StoreDTO getStoreByEmployee() {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            throw new ForbiddenException("You don't have permission to access this store");
        }

        return storeMapper.toDTO(currentUser.getStore());
    }

    @Override
    public StoreDTO moderateStore(Long id, StoreStatus status) {
        Store store = storeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Store", id)
        );
        store.setStatus(status);
        return storeMapper.toDTO(storeRepository.save(store));
    }
}
