package com.shadow.mapper;

import com.shadow.model.User;
import com.shadow.payload.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    UserDTO toDTO(User user);
}
