package com.shadow.service;

import com.shadow.exception.CategoryException;
import com.shadow.exception.ProductException;
import com.shadow.exception.UserException;
import com.shadow.model.User;
import com.shadow.payload.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto, User user) throws CategoryException, UserException;

    ProductDto updateProduct(Long id, ProductDto productDto, User user) throws ProductException, CategoryException, UserException;

    void deleteProduct(Long id, User user) throws ProductException;

    List<ProductDto> getAllProductByStoreId(Long storeId);

    List<ProductDto> searchByKeyword(Long storeId, String keyword);
}
