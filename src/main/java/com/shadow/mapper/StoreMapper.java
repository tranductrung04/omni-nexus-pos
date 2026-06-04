package com.shadow.mapper;

import com.shadow.model.Store;
import com.shadow.payload.dto.StoreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    @Mapping(source = "storeAdmin.id", target = "storeAdminId")
    StoreDTO toDTO(Store store);

    @Mapping(target = "storeAdmin", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Store toEntity(StoreDTO storeDto);
}
