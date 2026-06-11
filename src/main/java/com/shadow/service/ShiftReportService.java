package com.shadow.service;

import com.shadow.payload.dto.ShiftReportDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ShiftReportService {
    ShiftReportDTO startShift();

    ShiftReportDTO endShift();

    ShiftReportDTO getShiftReportById(Long id);

    List<ShiftReportDTO> getAllShiftReports();

    List<ShiftReportDTO> getAllShiftReportsByBranchId(Long branchId);

    List<ShiftReportDTO> getAllShiftReportsByCashierId(Long cashierId);

    ShiftReportDTO getCurrentShiftProcess();

    ShiftReportDTO getShiftReportByCashierIdAndDate(Long cashierId, LocalDateTime date);
}