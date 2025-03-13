package com.example.selling_cars.controller;

import com.example.selling_cars.dto.AdminDashboardDTO;
import com.example.selling_cars.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin-dashboard")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService adminDashboardService;

    // Lấy tất cả bản ghi tổng quan (cho admin)
    @GetMapping
    public ResponseEntity<List<AdminDashboardDTO>> getAllDashboards() {
        return ResponseEntity.ok(adminDashboardService.getAllDashboards());
    }

    // Lấy bản ghi tổng quan mới nhất (cho admin)
    @GetMapping("/latest")
    public ResponseEntity<AdminDashboardDTO> getLatestDashboard() {
        return adminDashboardService.getLatestDashboard()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lấy bản ghi tổng quan theo ngày (cho admin)
    @GetMapping("/date")
    public ResponseEntity<AdminDashboardDTO> getDashboardByDate(@RequestParam String reportDate) {
        LocalDate date = LocalDate.parse(reportDate);
        return adminDashboardService.getDashboardByDate(date)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Cập nhật dữ liệu tổng quan (dùng stored procedure)
    @PostMapping("/update")
    public ResponseEntity<AdminDashboardDTO> updateDashboard() {
        return ResponseEntity.ok(adminDashboardService.updateDashboard());
    }

    // Tính toán thủ công dữ liệu tổng quan
    @PostMapping("/calculate")
    public ResponseEntity<AdminDashboardDTO> calculateDashboardManually() {
        return ResponseEntity.ok(adminDashboardService.calculateDashboardManually());
    }

    // Xóa bản ghi tổng quan (cho admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDashboard(@PathVariable Integer id) {
        adminDashboardService.deleteDashboard(id);
        return ResponseEntity.noContent().build();
    }
}