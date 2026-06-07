package com.shadow.service.impl;

import com.shadow.domain.OrderStatus;
import com.shadow.domain.PaymentType;
import com.shadow.exception.ResourceNotFoundException;
import com.shadow.mapper.OrderItemMapper;
import com.shadow.mapper.OrderMapper;
import com.shadow.model.*;
import com.shadow.payload.dto.OrderDTO;
import com.shadow.payload.dto.OrderItemDTO;
import com.shadow.repository.OrderRepository;
import com.shadow.service.CustomerService;
import com.shadow.service.OrderService;
import com.shadow.service.ProductService;
import com.shadow.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Transactional
    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        if (orderDTO.getItems() == null || orderDTO.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        User cashier = userService.getCurrentUser();
        Branch branch = cashier.getBranch();
        if (branch == null) {
            throw new ResourceNotFoundException("Branch", "cashier");
        }
        Customer customer = customerService.getCustomer(orderDTO.getCustomerId());

        List<Long> productIds = orderDTO.getItems().stream()
                .map(OrderItemDTO::getProductId).toList();
        Map<Long, Product> productMap = productService.getProductsByIds(productIds).stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        Order order = orderMapper.toEntity(orderDTO);
        order.setBranch(branch);
        order.setCashier(cashier);
        order.setCustomer(customer);

        double totalAmount = 0;
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderDTO.getItems()) {
            Product product = productMap.get(orderItemDTO.getProductId());
            if (product == null) {
                throw new ResourceNotFoundException("Product", orderItemDTO.getProductId());
            }

            double itemPrice = product.getSellingPrice() * orderItemDTO.getQuantity();
            totalAmount += itemPrice;

            OrderItem orderItem = orderItemMapper.toEntity(orderItemDTO);
            orderItem.setPrice(itemPrice);
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            items.add(orderItem);
        }
        order.setItems(items);
        order.setTotalAmount(totalAmount);

        return orderMapper.toDTO(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Order", id)
        );
        orderRepository.delete(order);
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        return orderMapper.toDTO(orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Order", id)
        ));
    }

    @Override
    public List<OrderDTO> getAllOrdersByBranch(
            Long branchId,
            Long customerId,
            Long cashierId,
            PaymentType paymentType,
            OrderStatus status) {
        return orderRepository.findByBranchId(branchId).stream()
                .filter(
                        order -> customerId == null
                                || (order.getCustomer() != null && order.getCustomer().getId().equals(customerId))
                )
                .filter(
                        order -> cashierId == null
                                || (order.getCashier() != null && order.getCashier().getId().equals(cashierId))
                )
                .filter(
                        order -> paymentType == null
                                || (order.getPaymentType() != null && order.getPaymentType().equals(paymentType))
                )
                .filter(
                        order -> status == null
                                || (order.getStatus() != null && order.getStatus().equals(status))
                )
                .map(orderMapper::toDTO).toList();
    }

    @Override
    public List<OrderDTO> getAllOrdersByCashierId(Long cashierId) {
        return orderRepository.findByCashierId(cashierId).stream()
                .map(orderMapper::toDTO).toList();
    }

    @Override
    public List<OrderDTO> getAllOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(orderMapper::toDTO).toList();
    }

    @Override
    public List<OrderDTO> getTodayOrdersByBranchId(Long branchId) {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();
        return orderRepository.findByBranchIdAndCreatedAtBetween(branchId, start, end).stream()
                .map(orderMapper::toDTO).toList();
    }

    @Override
    public List<OrderDTO> getTop5RecentOrderByBranchId(Long branchId) {
        return orderRepository.findTop5ByBranchIdOrderByCreatedAtDesc(branchId).stream()
                .map(orderMapper::toDTO).toList();
    }
}
