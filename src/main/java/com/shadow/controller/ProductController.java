package com.shadow.controller;

import com.shadow.exception.CategoryException;
import com.shadow.exception.ProductException;
import com.shadow.exception.UserException;
import com.shadow.payload.dto.ProductDTO;
import com.shadow.payload.response.ApiResponse;
import com.shadow.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
            @RequestBody ProductDTO productDto) throws CategoryException, UserException {
        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ProductDTO>> getProductByStoreId(
            @PathVariable Long storeId) {
        return ResponseEntity.ok(productService.getAllProductByStoreId(storeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDTO productDto) throws ProductException, CategoryException, UserException {
        return ResponseEntity.ok(productService.updateProduct(id, productDto));
    }

    @GetMapping("/store/{storeId}/search")
    public ResponseEntity<List<ProductDTO>> searchProductByKeyword(
            @PathVariable Long storeId,
            @RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchByKeyword(storeId, keyword));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(
            @PathVariable Long id) throws ProductException {
        productService.deleteProduct(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Product deleted successfully");

        return ResponseEntity.ok(apiResponse);
    }
}
