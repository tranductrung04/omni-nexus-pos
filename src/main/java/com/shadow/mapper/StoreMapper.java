package com.shadow.mapper;

import com.shadow.model.Store;
import com.shadow.payload.dto.StoreDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    @Mapping(source = "storeAdmin.id", target = "storeAdminId")
    StoreDto toDTO(Store store);

    Store toEntity(StoreDto storeDto);
}
