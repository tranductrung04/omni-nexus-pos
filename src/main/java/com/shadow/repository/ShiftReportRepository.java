package com.shadow.repository;

import com.shadow.model.ShiftReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShiftReportRepository extends JpaRepository<ShiftReport, Long> {
    List<ShiftReport> findByCashierId(Long cashierId);

    List<ShiftReport> findByBranchId(Long branchId);

    Optional<ShiftReport> findTopByCashierIdAndShiftEndIsNullOrderByShiftStartDesc(Long cashierId);

    Optional<ShiftReport> findByCashierIdAndShiftStartBetween(
            Long cashierId,
            LocalDateTime from,
            LocalDateTime to
    );
}
