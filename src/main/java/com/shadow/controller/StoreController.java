package com.shadow.controller;

import com.shadow.domain.StoreStatus;
import com.shadow.exception.UserException;
import com.shadow.model.Store;
import com.shadow.payload.dto.StoreDto;
import com.shadow.payload.response.ApiResponse;
import com.shadow.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<StoreDto> createStore(
            @RequestBody StoreDto storeDto) throws UserException {
        return ResponseEntity.ok(storeService.createStore(storeDto));
    }

    @GetMapping
    public ResponseEntity<List<StoreDto>> getAllStore() {
        return ResponseEntity.ok(storeService.getAllStores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDto> getStoreById(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDto> updateStore(
            @PathVariable Long id,
            @RequestBody StoreDto storeDto) throws UserException {
        return ResponseEntity.ok(storeService.updateStore(id, storeDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StoreDto> moderateStore(
            @PathVariable Long id, @RequestParam StoreStatus status) {
        return ResponseEntity.ok(storeService.moderateStore(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStore(
            @PathVariable Long id) throws UserException {
        storeService.deleteStore(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Store deleted successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/admin")
    public ResponseEntity<Store> getStoreByAdmin() throws UserException {
        return ResponseEntity.ok(storeService.getStoreByAdmin());
    }

    @GetMapping("/employee")
    public ResponseEntity<StoreDto> getStoreByEmployee() throws UserException {
        return ResponseEntity.ok(storeService.getStoreByEmployee());
    }

}
