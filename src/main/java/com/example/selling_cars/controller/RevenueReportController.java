package com.example.selling_cars.controller;

import com.example.selling_cars.dto.RevenueReportDTO;
import com.example.selling_cars.service.RevenueReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/revenue-reports")
public class RevenueReportController {

    @Autowired
    private RevenueReportService revenueReportService;

    // Lấy tất cả báo cáo doanh thu (cho admin)
    @GetMapping
    public ResponseEntity<List<RevenueReportDTO>> getAllReports() {
        return ResponseEntity.ok(revenueReportService.getAllReports());
    }

    // Lấy báo cáo theo ID
    @GetMapping("/{id}")
    public ResponseEntity<RevenueReportDTO> getReportById(@PathVariable Integer id) {
        return revenueReportService.getReportById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lấy báo cáo theo năm (dùng stored procedure)
    @GetMapping("/year/{year}")
    public ResponseEntity<List<RevenueReportDTO>> getReportsByYear(@PathVariable Integer year) {
        return ResponseEntity.ok(revenueReportService.getReportsByYear(year));
    }

    // Lấy báo cáo theo tháng và năm
    @GetMapping("/month")
    public ResponseEntity<RevenueReportDTO> getReportByMonthAndYear(
            @RequestParam Integer month,
            @RequestParam Integer year) {
        return revenueReportService.getReportByMonthAndYear(month, year)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tạo báo cáo doanh thu tháng (dùng stored procedure)
    @PostMapping("/generate")
    public ResponseEntity<RevenueReportDTO> generateMonthlyReport(
            @RequestParam Integer month,
            @RequestParam Integer year) {
        return ResponseEntity.ok(revenueReportService.generateMonthlyReport(month, year));
    }

    // Tạo báo cáo doanh thu tháng thủ công
    @PostMapping("/generate-manual")
    public ResponseEntity<RevenueReportDTO> generateMonthlyReportManually(
            @RequestParam Integer month,
            @RequestParam Integer year) {
        return ResponseEntity.ok(revenueReportService.generateMonthlyReportManually(month, year));
    }

    // Xóa báo cáo (cho admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Integer id) {
        revenueReportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}