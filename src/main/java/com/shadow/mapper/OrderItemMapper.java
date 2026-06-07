package com.shadow.mapper;

import com.shadow.model.OrderItem;
import com.shadow.payload.dto.OrderItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "order.id", target = "orderId")
    OrderItemDTO toDTO(OrderItem orderItem);

    @Mapping(target = "price", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    OrderItem toEntity(OrderItemDTO orderItemDTO);
}
