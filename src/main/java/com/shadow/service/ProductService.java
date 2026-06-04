package com.shadow.service;

import com.shadow.payload.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDto);

    ProductDTO updateProduct(Long id, ProductDTO productDto);

    void deleteProduct(Long id);

    List<ProductDTO> getAllProductByStoreId(Long storeId);

    List<ProductDTO> searchByKeyword(Long storeId, String keyword);
}
