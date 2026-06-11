package com.shadow.mapper;

import com.shadow.model.ShiftReport;
import com.shadow.payload.dto.ShiftReportDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShiftReportMapper {
    @Mapping(source = "cashier.id", target = "cashierId")
    @Mapping(source = "branch.id", target = "branchId")
    ShiftReportDTO toDTO(ShiftReport shiftReport);

    @Mapping(target = "paymentSummaries", ignore = true)
    @Mapping(target = "topSellingProducts", ignore = true)
    @Mapping(target = "recentOrders", ignore = true)
    @Mapping(target = "refunds", ignore = true)
    @Mapping(target = "cashier", ignore = true)
    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ShiftReport toEntity(ShiftReportDTO shiftReportDTO);
}
