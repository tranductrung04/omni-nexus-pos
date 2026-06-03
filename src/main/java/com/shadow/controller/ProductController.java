package com.shadow.controller;

import com.shadow.exception.CategoryException;
import com.shadow.exception.ProductException;
import com.shadow.exception.UserException;
import com.shadow.model.User;
import com.shadow.payload.dto.ProductDto;
import com.shadow.payload.response.ApiResponse;
import com.shadow.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto,
            @AuthenticationPrincipal User user) throws CategoryException, UserException {
        return ResponseEntity.ok(productService.createProduct(productDto, user));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ProductDto>> getProductByStoreId(
            @PathVariable Long storeId) {
        return ResponseEntity.ok(productService.getAllProductByStoreId(storeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDto productDto,
            @AuthenticationPrincipal User user) throws ProductException, CategoryException, UserException {
        return ResponseEntity.ok(productService.updateProduct(id, productDto, user));
    }

    @GetMapping("/store/{storeId}/search")
    public ResponseEntity<List<ProductDto>> searchProductByKeyword(
            @PathVariable Long storeId,
            @RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchByKeyword(storeId, keyword));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) throws ProductException {
        productService.deleteProduct(id, user);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Product deleted successfully");

        return ResponseEntity.ok(apiResponse);
    }
}
