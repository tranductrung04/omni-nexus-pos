package com.shadow.mapper;

import com.shadow.model.Product;
import com.shadow.payload.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "category.id", target = "categoryId")
    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO productDto);
}