package com.shadow.mapper;

import com.shadow.model.Branch;
import com.shadow.payload.dto.BranchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "manager.id", target = "managerId")
    BranchDTO toDTO(Branch branch);

    @Mapping(target = "store", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Branch toEntity(BranchDTO branchDTO);
}