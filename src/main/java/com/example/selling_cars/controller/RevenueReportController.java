package com.example.selling_cars.controller;

import com.example.selling_cars.dto.RevenueReportDTO;
import com.example.selling_cars.entity.RevenueReport;
import com.example.selling_cars.mapper.RevenueReportMapper;
import com.example.selling_cars.service.RevenueReportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/revenue")
@CrossOrigin(origins = "*")
public class RevenueReportController {

    @Autowired
    private RevenueReportService revenueReportService;

    @Autowired
    private RevenueReportMapper revenueReportMapper;

    @GetMapping
    public ResponseEntity<List<RevenueReportDTO>> getAllReports() {
        List<RevenueReport> reports = revenueReportService.getAllReports();
        List<RevenueReportDTO> reportDTOs = reports.stream()
                .map(revenueReportMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RevenueReportDTO> getReportById(@PathVariable Integer id) {
        RevenueReport report = revenueReportService.getReportById(id);
        return ResponseEntity.ok(revenueReportMapper.toDTO(report));
    }

    @GetMapping("/month/{year}/{month}")
    public ResponseEntity<RevenueReportDTO> getReportByYearMonth(
            @PathVariable Integer year,
            @PathVariable Integer month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        Optional<RevenueReport> reportOpt = revenueReportService.getReportByYearMonth(yearMonth);
        return reportOpt
                .map(report -> ResponseEntity.ok(revenueReportMapper.toDTO(report)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<RevenueReportDTO>> getReportsByYear(@PathVariable Integer year) {
        List<RevenueReport> reports = revenueReportService.getReportsByYear(year);
        List<RevenueReportDTO> reportDTOs = reports.stream()
                .map(revenueReportMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDTOs);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<RevenueReportDTO>> getReportsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<RevenueReport> reports = revenueReportService.getReportsByDateRange(startDate, endDate);
        List<RevenueReportDTO> reportDTOs = reports.stream()
                .map(revenueReportMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDTOs);
    }

    @GetMapping("/latest")
    public ResponseEntity<RevenueReportDTO> getLatestReport() {
        Optional<RevenueReport> latestReport = revenueReportService.getLatestReport();
        return latestReport
                .map(report -> ResponseEntity.ok(revenueReportMapper.toDTO(report)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/min-revenue")
    public ResponseEntity<List<RevenueReportDTO>> getReportsByMinRevenue(
            @RequestParam BigDecimal minRevenue) {
        List<RevenueReport> reports = revenueReportService.getReportsByMinRevenue(minRevenue);
        List<RevenueReportDTO> reportDTOs = reports.stream()
                .map(revenueReportMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDTOs);
    }

    @GetMapping("/min-orders")
    public ResponseEntity<List<RevenueReportDTO>> getReportsByMinOrders(
            @RequestParam Integer minOrders) {
        List<RevenueReport> reports = revenueReportService.getReportsByMinOrders(minOrders);
        List<RevenueReportDTO> reportDTOs = reports.stream()
                .map(revenueReportMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDTOs);
    }

    @GetMapping("/min-average-value")
    public ResponseEntity<List<RevenueReportDTO>> getReportsByMinAverageOrderValue(
            @RequestParam BigDecimal minAverage) {
        List<RevenueReport> reports = revenueReportService.getReportsByMinAverageOrderValue(minAverage);
        List<RevenueReportDTO> reportDTOs = reports.stream()
                .map(revenueReportMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDTOs);
    }

    @PostMapping
    public ResponseEntity<RevenueReportDTO> createReport(@Valid @RequestBody RevenueReportDTO reportDTO) {
        RevenueReport report = revenueReportMapper.toEntity(reportDTO);
        RevenueReport savedReport = revenueReportService.createReport(report);
        return new ResponseEntity<>(revenueReportMapper.toDTO(savedReport), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RevenueReportDTO> updateReport(
            @PathVariable Integer id,
            @Valid @RequestBody RevenueReportDTO reportDTO) {
        RevenueReport report = revenueReportMapper.toEntity(reportDTO);
        RevenueReport updatedReport = revenueReportService.updateReport(id, report);
        return ResponseEntity.ok(revenueReportMapper.toDTO(updatedReport));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Integer id) {
        revenueReportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/year-summary/{year}")
    public ResponseEntity<RevenueReportDTO> generateYearlySummary(@PathVariable Integer year) {
        RevenueReport summary = revenueReportService.generateYearlySummary(year);
        return ResponseEntity.ok(revenueReportMapper.toDTO(summary));
    }

    @GetMapping("/month-report/{year}/{month}")
    public ResponseEntity<RevenueReportDTO> generateMonthlyReport(
            @PathVariable Integer year, 
            @PathVariable Integer month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        RevenueReport report = revenueReportService.generateMonthlyReport(yearMonth);
        return ResponseEntity.ok(revenueReportMapper.toDTO(report));
    }

    @GetMapping("/top-products/{year}/{month}")
    public ResponseEntity<List<String>> getTopSellingProducts(
            @PathVariable Integer year,
            @PathVariable Integer month,
            @RequestParam(defaultValue = "5") Integer limit) {
        YearMonth yearMonth = YearMonth.of(year, month);
        List<String> topProducts = revenueReportService.getTopSellingProducts(yearMonth, limit);
        return ResponseEntity.ok(topProducts);
    }

    @GetMapping("/revenue-by-category/{year}/{month}")
    public ResponseEntity<List<Object[]>> getRevenueByCategoryForMonth(
            @PathVariable Integer year,
            @PathVariable Integer month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        List<Object[]> categoryRevenue = revenueReportService.getRevenueByCategoryForMonth(yearMonth);
        return ResponseEntity.ok(categoryRevenue);
    }
} 