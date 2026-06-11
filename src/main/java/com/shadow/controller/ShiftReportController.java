package com.shadow.controller;

import com.shadow.payload.dto.ShiftReportDTO;
import com.shadow.service.ShiftReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shift-reports")
public class ShiftReportController {
    private final ShiftReportService shiftReportService;

    @PostMapping("/start")
    public ResponseEntity<ShiftReportDTO> startShift() {
        return ResponseEntity.ok(shiftReportService.startShift());
    }

    @PostMapping("/end")
    public ResponseEntity<ShiftReportDTO> endShift() {
        return ResponseEntity.ok(shiftReportService.endShift());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftReportDTO> getShiftReportById(
            @PathVariable Long id) {
        return ResponseEntity.ok(shiftReportService.getShiftReportById(id));
    }

    @GetMapping
    public ResponseEntity<List<ShiftReportDTO>> getAllShiftReports() {
        return ResponseEntity.ok(shiftReportService.getAllShiftReports());
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<ShiftReportDTO>> getAllShiftReportsByBranchId(
            @PathVariable Long branchId) {
        return ResponseEntity.ok(shiftReportService.getAllShiftReportsByBranchId(branchId));
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<ShiftReportDTO>> getAllShiftReportsByCashierId(
            @PathVariable Long cashierId) {
        return ResponseEntity.ok(shiftReportService.getAllShiftReportsByCashierId(cashierId));
    }

    @GetMapping("/current")
    public ResponseEntity<ShiftReportDTO> getCurrentShiftProcess() {
        return ResponseEntity.ok(shiftReportService.getCurrentShiftProcess());
    }

    @GetMapping("/cashier/{cashierId}/by-date")
    public ResponseEntity<ShiftReportDTO> getShiftReportByCashierIdAndDate(
            @PathVariable Long cashierId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime date) {
        return ResponseEntity.ok(shiftReportService.getShiftReportByCashierIdAndDate(cashierId, date));
    }
}
