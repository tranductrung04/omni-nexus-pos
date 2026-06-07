package com.shadow.controller;

import com.shadow.domain.OrderStatus;
import com.shadow.domain.PaymentType;
import com.shadow.payload.dto.OrderDTO;
import com.shadow.payload.response.ApiResponse;
import com.shadow.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(
            @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteOrder(
            @PathVariable Long id) {
        orderService.deleteOrder(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Order deleted successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(
            @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("branch/{branchId}")
    public ResponseEntity<List<OrderDTO>> getAllOrdersByBranch(
            @PathVariable Long branchId,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long cashierId,
            @RequestParam(required = false) PaymentType paymentType,
            @RequestParam(required = false) OrderStatus status) {
        return ResponseEntity.ok(orderService.getAllOrdersByBranch(branchId, customerId, cashierId, paymentType, status));
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<OrderDTO>> getAllOrdersByCashierId(
            @PathVariable Long cashierId) {
        return ResponseEntity.ok(orderService.getAllOrdersByCashierId(cashierId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDTO>> getAllOrdersByCustomerId(
            @PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getAllOrdersByCustomerId(customerId));
    }

    @GetMapping("/today/branch/{branchId}")
    public ResponseEntity<List<OrderDTO>> getAllTodayOrders(
            @PathVariable Long branchId) {
        return ResponseEntity.ok(orderService.getTodayOrdersByBranchId(branchId));
    }

    @GetMapping("/recent/branch/{branchId}")
    public ResponseEntity<List<OrderDTO>> getRecentOrders(
            @PathVariable Long branchId) {
        return ResponseEntity.ok(orderService.getTop5RecentOrderByBranchId(branchId));
    }
}
