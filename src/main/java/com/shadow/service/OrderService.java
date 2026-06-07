package com.shadow.service;

import com.shadow.domain.OrderStatus;
import com.shadow.domain.PaymentType;
import com.shadow.model.Order;
import com.shadow.payload.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);

    void deleteOrder(Long id);

    OrderDTO getOrderById(Long id);

    Order getOrderEntityById(Long id);

    List<OrderDTO> getAllOrdersByBranch(
            Long branchId,
            Long customerId,
            Long cashierId,
            PaymentType paymentType,
            OrderStatus status
    );

    List<OrderDTO> getAllOrdersByCashierId(Long cashierId);

    List<OrderDTO> getAllOrdersByCustomerId(Long customerId);

    List<OrderDTO> getTodayOrdersByBranchId(Long branchId);

    List<OrderDTO> getTop5RecentOrderByBranchId(Long branchId);
}
