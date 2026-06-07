package com.shadow.controller;

import com.shadow.payload.dto.RefundDTO;
import com.shadow.payload.response.ApiResponse;
import com.shadow.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/refunds")
public class RefundController {
    private final RefundService refundService;

    @PostMapping
    public ResponseEntity<RefundDTO> createRefund(
            @RequestBody RefundDTO refundDTO) {
        return ResponseEntity.ok(refundService.createRefund(refundDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRefund(
            @PathVariable Long id) {
        refundService.deleteRefund(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Refund deleted successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<RefundDTO> getRefundById(
            @PathVariable Long id) {
        return ResponseEntity.ok(refundService.getRefundById(id));
    }

    @GetMapping
    public ResponseEntity<List<RefundDTO>> getAllRefunds() {
        return ResponseEntity.ok(refundService.getAllRefunds());
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<RefundDTO>> getAllRefundsByCashierId(
            @PathVariable Long cashierId) {
        return ResponseEntity.ok(refundService.getAllRefundsByCashierId(cashierId));
    }

    @GetMapping("/shift-report/{shiftReportId}")
    public ResponseEntity<List<RefundDTO>> getAllRefundsByShiftReportId(
            @PathVariable Long shiftReportId) {
        return ResponseEntity.ok(refundService.getAllRefundsByShiftReportId(shiftReportId));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<RefundDTO>> getAllRefundsByBranchId(
            @PathVariable Long branchId) {
        return ResponseEntity.ok(refundService.getAllRefundsByBranchId(branchId));
    }

    @GetMapping("/cashier/{cashierId}/range")
    public ResponseEntity<List<RefundDTO>> getAllRefundsByCashierIdAndDateRange(
            @PathVariable Long cashierId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(refundService.getAllRefundsByCashierIdAndDateRange(
                cashierId, startDate, endDate
        ));
    }
}
