package com.shadow.mapper;

import com.shadow.model.User;
import com.shadow.payload.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(target = "password", ignore = true)
    UserDTO toDTO(User user);

    @Mapping(target = "store", ignore = true)
    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserDTO userDTO);
}
