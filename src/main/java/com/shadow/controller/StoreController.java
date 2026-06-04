package com.shadow.controller;

import com.shadow.domain.StoreStatus;
import com.shadow.model.Store;
import com.shadow.payload.dto.StoreDTO;
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
    public ResponseEntity<StoreDTO> createStore(
            @RequestBody StoreDTO storeDto) {
        return ResponseEntity.ok(storeService.createStore(storeDto));
    }

    @GetMapping
    public ResponseEntity<List<StoreDTO>> getAllStore() {
        return ResponseEntity.ok(storeService.getAllStores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDTO> getStoreById(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDTO> updateStore(
            @PathVariable Long id,
            @RequestBody StoreDTO storeDto) {
        return ResponseEntity.ok(storeService.updateStore(id, storeDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StoreDTO> moderateStore(
            @PathVariable Long id, @RequestParam StoreStatus status) {
        return ResponseEntity.ok(storeService.moderateStore(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStore(
            @PathVariable Long id) {
        storeService.deleteStore(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Store deleted successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/admin")
    public ResponseEntity<Store> getStoreByAdmin() {
        return ResponseEntity.ok(storeService.getStoreByAdmin());
    }

    @GetMapping("/employee")
    public ResponseEntity<StoreDTO> getStoreByEmployee() {
        return ResponseEntity.ok(storeService.getStoreByEmployee());
    }

}
