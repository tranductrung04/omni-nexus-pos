package com.shadow.service.impl;

import com.shadow.exception.ResourceNotFoundException;
import com.shadow.mapper.RefundMapper;
import com.shadow.model.Branch;
import com.shadow.model.Order;
import com.shadow.model.Refund;
import com.shadow.model.User;
import com.shadow.payload.dto.RefundDTO;
import com.shadow.repository.RefundRepository;
import com.shadow.service.OrderService;
import com.shadow.service.RefundService;
import com.shadow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {
    private final RefundRepository refundRepository;
    private final UserService userService;
    private final OrderService orderService;
    private final RefundMapper refundMapper;

    @Override
    public RefundDTO createRefund(RefundDTO refundDTO) {
        User cashier = userService.getCurrentUser();

        Order order = orderService.getOrderEntityById(refundDTO.getOrderId());

        Branch branch = order.getBranch();

        Refund refund = refundMapper.toEntity(refundDTO);
        refund.setOrder(order);
        refund.setCashier(cashier);
        refund.setBranch(branch);

        return refundMapper.toDTO(refundRepository.save(refund));
    }

    @Override
    public RefundDTO getRefundById(Long id) {
        return refundMapper.toDTO(refundRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Refund", id)
        ));
    }

    @Override
    public void deleteRefund(Long id) {
        Refund refund = refundRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Refund", id)
        );
        refundRepository.delete(refund);
    }

    @Override
    public List<RefundDTO> getAllRefunds() {
        return refundRepository.findAll().stream()
                .map(refundMapper::toDTO).toList();
    }

    @Override
    public List<RefundDTO> getAllRefundsByCashierId(Long cashierId) {
        return refundRepository.findByCashierId(cashierId).stream()
                .map(refundMapper::toDTO).toList();
    }

    @Override
    public List<RefundDTO> getAllRefundsByShiftReportId(Long shiftReportId) {
        return refundRepository.findByShiftReportId(shiftReportId).stream()
                .map(refundMapper::toDTO).toList();
    }

    @Override
    public List<RefundDTO> getAllRefundsByBranchId(Long branchId) {
        return refundRepository.findByBranchId(branchId).stream()
                .map(refundMapper::toDTO).toList();
    }

    @Override
    public List<RefundDTO> getAllRefundsByCashierIdAndDateRange(
            Long cashierId, LocalDateTime startDate, LocalDateTime endDate) {
        return refundRepository.findByCashierIdAndCreatedAtBetween(cashierId, startDate, endDate).stream()
                .map(refundMapper::toDTO).toList();
    }
}
