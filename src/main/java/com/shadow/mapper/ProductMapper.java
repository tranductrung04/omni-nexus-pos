package com.shadow.mapper;

import com.shadow.model.Category;
import com.shadow.model.Product;
import com.shadow.model.Store;
import com.shadow.payload.dto.ProductDto;

public class ProductMapper {
    public static ProductDto toDTO(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .description(product.getDescription())
                .mrp(product.getMrp())
                .sellingPrice(product.getSellingPrice())
                .brand(product.getBrand())
                .storeId(product.getStore() != null ? product.getStore().getId() : null)
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .imageUrl(product.getImageUrl())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public static Product toEntity(ProductDto productDto, Store store, Category category) {
        return Product.builder()
                .name(productDto.getName())
                .store(store)
                .category(category)
                .sku(productDto.getSku())
                .description(productDto.getDescription())
                .mrp(productDto.getMrp())
                .sellingPrice(productDto.getSellingPrice())
                .brand(productDto.getBrand())
                .imageUrl(productDto.getImageUrl())
                .build();
    }
}
