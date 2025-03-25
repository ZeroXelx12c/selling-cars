package com.example.selling_cars.controller;

import com.example.selling_cars.dto.AdminDashboardDTO;
import com.example.selling_cars.entity.AdminDashboard;
import com.example.selling_cars.exception.ResourceNotFoundException;
import com.example.selling_cars.mapper.AdminDashboardMapper;
import com.example.selling_cars.service.AdminDashboardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/dashboard")
@CrossOrigin(origins = "*")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService dashboardService;

    @Autowired
    private AdminDashboardMapper dashboardMapper;

    @GetMapping
    public ResponseEntity<List<AdminDashboardDTO>> getAllReports() {
        List<AdminDashboard> reports = dashboardService.getAllReports();
        List<AdminDashboardDTO> reportDTOs = reports.stream()
                .map(dashboardMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDashboardDTO> getReportById(@PathVariable Integer id) {
        AdminDashboard report = dashboardService.getReportById(id);
        return ResponseEntity.ok(dashboardMapper.toDTO(report));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<AdminDashboardDTO> getReportByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Optional<AdminDashboard> reportOpt = dashboardService.getReportByDate(date);
        AdminDashboard report = reportOpt.orElseThrow(() ->
                new ResourceNotFoundException("AdminDashboard", "date", date));
        return ResponseEntity.ok(dashboardMapper.toDTO(report));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<AdminDashboardDTO>> getReportsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<AdminDashboard> reports = dashboardService.getReportsByDateRange(startDate, endDate);
        List<AdminDashboardDTO> reportDTOs = reports.stream()
                .map(dashboardMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDTOs);
    }

    @GetMapping("/latest")
    public ResponseEntity<AdminDashboardDTO> getLatestReport() {
        Optional<AdminDashboard> reportOpt = dashboardService.getLatestReport();
        AdminDashboard report = reportOpt.orElseThrow(() ->
                new ResourceNotFoundException("AdminDashboard", "latest", "report"));
        return ResponseEntity.ok(dashboardMapper.toDTO(report));
    }

    @GetMapping("/revenue")
    public ResponseEntity<List<AdminDashboardDTO>> getReportsByRevenue(
            @RequestParam BigDecimal minRevenue) {
        List<AdminDashboard> reports = dashboardService.getReportsByRevenue(minRevenue);
        List<AdminDashboardDTO> reportDTOs = reports.stream()
                .map(dashboardMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDTOs);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<AdminDashboardDTO>> getReportsByNewOrders(
            @RequestParam Integer minOrders) {
        List<AdminDashboard> reports = dashboardService.getReportsByNewOrders(minOrders);
        List<AdminDashboardDTO> reportDTOs = reports.stream()
                .map(dashboardMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDTOs);
    }

    @GetMapping("/inventory")
    public ResponseEntity<List<AdminDashboardDTO>> getReportsByInventory(
            @RequestParam Integer maxInventory) {
        List<AdminDashboard> reports = dashboardService.getReportsByInventory(maxInventory);
        List<AdminDashboardDTO> reportDTOs = reports.stream()
                .map(dashboardMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDTOs);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<AdminDashboardDTO>> getReportsByCustomers(
            @RequestParam Integer minCustomers) {
        List<AdminDashboard> reports = dashboardService.getReportsByCustomers(minCustomers);
        List<AdminDashboardDTO> reportDTOs = reports.stream()
                .map(dashboardMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDTOs);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<AdminDashboardDTO>> getAllReportsOrderByDate() {
        List<AdminDashboard> reports = dashboardService.getAllReportsOrderByDate();
        List<AdminDashboardDTO> reportDTOs = reports.stream()
                .map(dashboardMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDTOs);
    }

    @PostMapping
    public ResponseEntity<AdminDashboardDTO> createReport(@Valid @RequestBody AdminDashboardDTO reportDTO) {
        AdminDashboard report = dashboardMapper.toEntity(reportDTO);
        AdminDashboard savedReport = dashboardService.createReport(report);
        return new ResponseEntity<>(dashboardMapper.toDTO(savedReport), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminDashboardDTO> updateReport(
            @PathVariable Integer id,
            @Valid @RequestBody AdminDashboardDTO reportDTO) {
        AdminDashboard report = dashboardMapper.toEntity(reportDTO);
        AdminDashboard updatedReport = dashboardService.updateReport(id, report);
        return ResponseEntity.ok(dashboardMapper.toDTO(updatedReport));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Integer id) {
        dashboardService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count/date-range")
    public ResponseEntity<Long> countReportsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        long count = dashboardService.countReportsByDateRange(startDate, endDate);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/generate")
    public ResponseEntity<AdminDashboardDTO> generateDailyReport() {
        AdminDashboard generatedReport = dashboardService.generateDailyReport();
        return ResponseEntity.ok(dashboardMapper.toDTO(generatedReport));
    }
} 