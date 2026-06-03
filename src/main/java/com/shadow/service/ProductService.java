package com.shadow.service;

import com.shadow.exception.CategoryException;
import com.shadow.exception.ProductException;
import com.shadow.exception.UserException;
import com.shadow.payload.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto) throws CategoryException, UserException;

    ProductDto updateProduct(Long id, ProductDto productDto) throws ProductException, CategoryException, UserException;

    void deleteProduct(Long id) throws ProductException;

    List<ProductDto> getAllProductByStoreId(Long storeId);

    List<ProductDto> searchByKeyword(Long storeId, String keyword);
}
