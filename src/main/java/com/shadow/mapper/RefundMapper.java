package com.shadow.mapper;

import com.shadow.model.Refund;
import com.shadow.payload.dto.RefundDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RefundMapper {
    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "shiftReport.id", target = "shiftReportId")
    @Mapping(source = "cashier.id", target = "cashierId")
    @Mapping(source = "branch.id", target = "branchId")
    RefundDTO toDTO(Refund refund);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "shiftReport", ignore = true)
    @Mapping(target = "cashier", ignore = true)
    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Refund toEntity(RefundDTO refundDTO);
}
