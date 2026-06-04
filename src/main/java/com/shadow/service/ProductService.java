package com.shadow.service;

import com.shadow.exception.CategoryException;
import com.shadow.exception.ProductException;
import com.shadow.exception.UserException;
import com.shadow.payload.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDto) throws CategoryException, UserException;

    ProductDTO updateProduct(Long id, ProductDTO productDto) throws ProductException, CategoryException, UserException;

    void deleteProduct(Long id) throws ProductException;

    List<ProductDTO> getAllProductByStoreId(Long storeId);

    List<ProductDTO> searchByKeyword(Long storeId, String keyword);
}
