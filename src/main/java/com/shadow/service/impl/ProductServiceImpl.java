package com.shadow.service.impl;

import com.shadow.exception.ResourceNotFoundException;
import com.shadow.mapper.ProductMapper;
import com.shadow.model.Category;
import com.shadow.model.Product;
import com.shadow.model.Store;
import com.shadow.payload.dto.ProductDTO;
import com.shadow.repository.ProductRepository;
import com.shadow.service.CategoryService;
import com.shadow.service.ProductService;
import com.shadow.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final StoreService storeService;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    @Override
    public ProductDTO createProduct(ProductDTO productDto) {
        Store store = storeService.getStoreEntityById(productDto.getStoreId());

        Category category = categoryService.getCategoryEntityById(productDto.getCategoryId());

        Product product = productMapper.toEntity(productDto);
        product.setStore(store);
        product.setCategory(category);

        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDto) {

        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product", id)
        );

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setSku(productDto.getSku());
        product.setImageUrl(productDto.getImageUrl());
        product.setMrp(productDto.getMrp());
        product.setSellingPrice(productDto.getSellingPrice());
        product.setBrand(productDto.getBrand());

        if (productDto.getCategoryId() != null) {
            Category category = categoryService.getCategoryEntityById(productDto.getCategoryId());
            product.setCategory(category);
        }

        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product", id)
        );

        productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> getAllProductsByStoreId(Long storeId) {
        List<Product> products = productRepository.findByStoreId(storeId);
        return products.stream().map(productMapper::toDTO).toList();
    }

    @Override
    public List<ProductDTO> searchByKeyword(Long storeId, String keyword) {
        List<Product> products = productRepository.searchByKeyword(storeId, keyword);
        return products.stream().map(productMapper::toDTO).toList();
    }

    @Override
    public Product getProductEntityById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product", id)
        );
    }
}
