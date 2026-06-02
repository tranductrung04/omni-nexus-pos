package com.shadow.service.impl;

import com.shadow.exception.ProductException;
import com.shadow.exception.StoreException;
import com.shadow.mapper.ProductMapper;
import com.shadow.model.Category;
import com.shadow.model.Product;
import com.shadow.model.Store;
import com.shadow.model.User;
import com.shadow.payload.dto.ProductDto;
import com.shadow.repository.CategoryRepository;
import com.shadow.repository.ProductRepository;
import com.shadow.repository.StoreRepository;
import com.shadow.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.catalog.CatalogException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto, User user) {
        Store store = storeRepository.findById(productDto.getStoreId()).orElseThrow(
                () -> new StoreException("Store not found")
        );

        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(
                () -> new CatalogException("Category not found")
        );

        Product product = ProductMapper.toEntity(productDto, store, category);

        return ProductMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto, User user) throws ProductException {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductException("Product not found")
        );

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setSku(productDto.getSku());
        product.setImageUrl(product.getImageUrl());
        product.setMrp(product.getMrp());
        product.setSellingPrice(productDto.getSellingPrice());
        product.setBrand(productDto.getBrand());
        product.setUpdatedAt(LocalDateTime.now());

        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(
                    () -> new CatalogException("Category not found")
            );
            product.setCategory(category);
        }

        return ProductMapper.toDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id, User user) throws ProductException {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductException("Product not found")
        );

        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> getAllProductByStoreId(Long storeId) {
        List<Product> products = productRepository.findByStoreId(storeId);
        return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchByKeyword(Long storeId, String keyword) {
        List<Product> products = productRepository.searchByKeyword(storeId, keyword);
        return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }
}
