package com.shadow.mapper;

import com.shadow.model.User;
import com.shadow.payload.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    UserDto toDTO(User user);
}
