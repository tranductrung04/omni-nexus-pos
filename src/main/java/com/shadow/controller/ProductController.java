package com.shadow.controller;

import com.shadow.exception.ProductException;
import com.shadow.exception.UserException;
import com.shadow.model.User;
import com.shadow.payload.dto.ProductDto;
import com.shadow.payload.response.ApiResponse;
import com.shadow.service.ProductService;
import com.shadow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto,
            @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);

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
            @RequestHeader("Authorization") String jwt) throws ProductException, UserException {
        User user = userService.getUserFromJwtToken(jwt);

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
            @RequestHeader("Authorization") String jwt) throws ProductException, UserException {
        User user = userService.getUserFromJwtToken(jwt);

        productService.deleteProduct(id, user);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Product deleted successfully");

        return ResponseEntity.ok(apiResponse);
    }
}
