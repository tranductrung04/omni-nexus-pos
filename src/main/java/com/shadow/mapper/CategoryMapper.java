package com.shadow.mapper;

import com.shadow.model.Category;
import com.shadow.payload.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(source = "store.id", target = "storeId")
    CategoryDTO toDTO(Category category);
}
