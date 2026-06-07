package com.shadow.mapper;

import com.shadow.model.Order;
import com.shadow.payload.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(source = "cashier.id", target = "cashierId")
    @Mapping(source = "customer.id", target = "customerId")
    OrderDTO toDTO(Order order);

    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "cashier", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Order toEntity(OrderDTO orderDTO);
}
