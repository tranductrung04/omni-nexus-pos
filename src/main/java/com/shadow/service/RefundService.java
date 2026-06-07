package com.shadow.service;

import com.shadow.payload.dto.RefundDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface RefundService {
    RefundDTO createRefund(RefundDTO refundDTO);

    RefundDTO getRefundById(Long id);

    void deleteRefund(Long id);

    List<RefundDTO> getAllRefunds();

    List<RefundDTO> getAllRefundsByCashierId(Long cashierId);

    List<RefundDTO> getAllRefundsByShiftReportId(Long shiftReportId);

    List<RefundDTO> getAllRefundsByBranchId(Long branchId);

    List<RefundDTO> getAllRefundsByCashierIdAndDateRange(
            Long cashierId, LocalDateTime startDate, LocalDateTime endDate);
}
