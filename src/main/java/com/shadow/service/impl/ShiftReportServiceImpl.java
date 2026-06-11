package com.shadow.service.impl;

import com.shadow.domain.PaymentType;
import com.shadow.exception.DuplicateResourceException;
import com.shadow.exception.ResourceNotFoundException;
import com.shadow.mapper.ShiftReportMapper;
import com.shadow.model.*;
import com.shadow.payload.dto.ShiftReportDTO;
import com.shadow.repository.OrderRepository;
import com.shadow.repository.RefundRepository;
import com.shadow.repository.ShiftReportRepository;
import com.shadow.service.ShiftReportService;
import com.shadow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftReportServiceImpl implements ShiftReportService {
    private final ShiftReportRepository shiftReportRepository;
    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ShiftReportMapper shiftReportMapper;

    @Override
    public ShiftReportDTO startShift() {
        User cashier = userService.getCurrentUser();

        Branch branch = cashier.getBranch();

        LocalDateTime shiftStart = LocalDateTime.now();
        LocalDateTime startOfDay = shiftStart.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = shiftStart.withHour(23).withMinute(59).withSecond(59);

        Optional<ShiftReport> existing = shiftReportRepository
                .findByCashierIdAndShiftStartBetween(cashier.getId(), startOfDay, endOfDay);

        if (existing.isPresent()) {
            throw new DuplicateResourceException("Shift already starts");
        }

        ShiftReport shiftReport = new ShiftReport();
        shiftReport.setShiftStart(shiftStart);
        shiftReport.setCashier(cashier);
        shiftReport.setBranch(branch);

        return shiftReportMapper.toDTO(shiftReportRepository.save(shiftReport));
    }

    @Override
    public ShiftReportDTO endShift() {
        User cashier = userService.getCurrentUser();

        ShiftReport shiftReport = shiftReportRepository
                .findTopByCashierIdAndShiftEndIsNullOrderByShiftStartDesc(cashier.getId()).orElseThrow(
                        () -> new ResourceNotFoundException("ShiftReport", "with cashierId" + cashier.getId())
                );

        LocalDateTime now = LocalDateTime.now();
        List<Refund> refunds = refundRepository.findByCashierIdAndCreatedAtBetween(
                cashier.getId(), shiftReport.getShiftStart(), now);
        double totalRefunds = refunds.stream()
                .filter(refund -> refund.getAmount() != null)
                .mapToDouble(Refund::getAmount).sum();

        List<Order> orders = orderRepository.findByCashierIdAndCreatedAtBetween(
                cashier.getId(), shiftReport.getShiftStart(), now);
        int totalOrders = orders.size();
        double totalSales = orders.stream()
                .filter(order -> order.getTotalAmount() != null)
                .mapToDouble(Order::getTotalAmount).sum();

        double netSale = totalSales - totalRefunds;

        shiftReport.setShiftEnd(now);
        shiftReport.setTotalSales(totalSales);
        shiftReport.setTotalRefunds(totalRefunds);
        shiftReport.setNetSale(netSale);
        shiftReport.setTotalOrders(totalOrders);
        shiftReport.setPaymentSummaries(getPaymentSummaries(orders, totalSales));
        shiftReport.setTopSellingProducts(GetTopSellingProducts(orders));
        shiftReport.setRecentOrders(GetRecentOrders(orders));
        shiftReport.setRefunds(refunds);

        return shiftReportMapper.toDTO(shiftReportRepository.save(shiftReport));
    }

    @Override
    public ShiftReportDTO getShiftReportById(Long id) {
        return shiftReportMapper.toDTO(shiftReportRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("ShiftReport", id)
        ));
    }

    @Override
    public List<ShiftReportDTO> getAllShiftReports() {
        return shiftReportRepository.findAll().stream()
                .map(shiftReportMapper::toDTO).toList();
    }

    @Override
    public List<ShiftReportDTO> getAllShiftReportsByBranchId(Long branchId) {
        return shiftReportRepository.findByBranchId(branchId).stream()
                .map(shiftReportMapper::toDTO).toList();
    }

    @Override
    public List<ShiftReportDTO> getAllShiftReportsByCashierId(Long cashierId) {
        return shiftReportRepository.findByCashierId(cashierId).stream()
                .map(shiftReportMapper::toDTO).toList();
    }

    @Override
    public ShiftReportDTO getCurrentShiftProcess() {
        User cashier = userService.getCurrentUser();

        ShiftReport shiftReport = shiftReportRepository
                .findTopByCashierIdAndShiftEndIsNullOrderByShiftStartDesc(cashier.getId()).orElseThrow(
                        () -> new ResourceNotFoundException("ShiftReport", "with cashierId: " + cashier.getId())
                );

        LocalDateTime now = LocalDateTime.now();
        List<Refund> refunds = refundRepository.findByCashierIdAndCreatedAtBetween(
                cashier.getId(), shiftReport.getShiftStart(), now);
        double totalRefunds = refunds.stream()
                .filter(refund -> refund.getAmount() != null)
                .mapToDouble(Refund::getAmount).sum();

        List<Order> orders = orderRepository.findByCashierIdAndCreatedAtBetween(
                cashier.getId(), shiftReport.getShiftStart(), now
        );
        int totalOrders = orders.size();
        double totalSales = orders.stream()
                .filter(order -> order.getTotalAmount() != null)
                .mapToDouble(Order::getTotalAmount).sum();

        double netSale = totalSales - totalRefunds;

        shiftReport.setTotalSales(totalSales);
        shiftReport.setTotalRefunds(totalRefunds);
        shiftReport.setNetSale(netSale);
        shiftReport.setTotalOrders(totalOrders);
        shiftReport.setPaymentSummaries(getPaymentSummaries(orders, totalSales));
        shiftReport.setTopSellingProducts(GetTopSellingProducts(orders));
        shiftReport.setRecentOrders(GetRecentOrders(orders));
        shiftReport.setRefunds(refunds);

        return shiftReportMapper.toDTO(shiftReport);
    }

    @Override
    public ShiftReportDTO getShiftReportByCashierIdAndDate(Long cashierId, LocalDateTime date) {
        User cashier = userService.getUserById(cashierId);

        LocalDateTime start = date.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = date.withHour(23).withMinute(59).withSecond(59);

        ShiftReport shiftReport = shiftReportRepository.findByCashierIdAndShiftStartBetween(
                cashierId, start, end).orElseThrow(
                () -> new ResourceNotFoundException("ShiftReport", "with cashierId: " + cashierId)
        );

        return shiftReportMapper.toDTO(shiftReport);
    }

    // ---------------------------------- Helper -----------------------------------------------------

    private List<PaymentSummary> getPaymentSummaries(List<Order> orders, double totalSales) {
        Map<PaymentType, List<Order>> grouped = orders.stream()
                .collect(Collectors.groupingBy(order ->
                        order.getPaymentType() != null ? order.getPaymentType() : PaymentType.CASH));

        List<PaymentSummary> summaries = new ArrayList<>();
        for (Map.Entry<PaymentType, List<Order>> entry : grouped.entrySet()) {
            double amount = entry.getValue().stream()
                    .mapToDouble(Order::getTotalAmount).sum();
            int transactions = entry.getValue().size();
            double percent = (amount / totalSales) * 100;

            PaymentSummary paymentSummary = new PaymentSummary();
            paymentSummary.setPaymentType(entry.getKey());
            paymentSummary.setTotalAmount(amount);
            paymentSummary.setTransactionCount(transactions);
            paymentSummary.setPercentage(percent);
            summaries.add(paymentSummary);
        }
        return summaries;
    }

    private List<Product> GetTopSellingProducts(List<Order> orders) {
        Map<Product, Long> productsMap = new HashMap<>();

        for (Order order : orders) {
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                productsMap.put(
                        product,
                        productsMap.getOrDefault(product, 0L) + item.getQuantity()
                );
            }
        }
        return productsMap.entrySet().stream()
                .sorted((a, b)
                        -> b.getValue().compareTo(a.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<Order> GetRecentOrders(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getCreatedAt).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}
