package com.shadow.service;

import com.shadow.model.Product;
import com.shadow.payload.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDto);

    ProductDTO updateProduct(Long id, ProductDTO productDto);

    void deleteProduct(Long id);

    List<ProductDTO> getAllProductsByStoreId(Long storeId);

    List<ProductDTO> searchByKeyword(Long storeId, String keyword);

    Product getProductEntityById(Long id);

    List<Product> getProductsByIds(List<Long> ids);
}
