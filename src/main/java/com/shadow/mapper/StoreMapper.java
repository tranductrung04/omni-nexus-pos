package com.shadow.mapper;

import com.shadow.model.Store;
import com.shadow.payload.dto.StoreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    @Mapping(source = "storeAdmin.id", target = "storeAdminId")
    StoreDTO toDTO(Store store);

    Store toEntity(StoreDTO storeDto);
}
