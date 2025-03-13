package com.example.selling_cars.service;

import com.example.selling_cars.dto.RevenueReportDTO;
import com.example.selling_cars.entity.RevenueReport;
import com.example.selling_cars.repository.PaymentsRepository;
import com.example.selling_cars.repository.RevenueReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RevenueReportService {

    @Autowired
    private RevenueReportRepository revenueReportRepository;

    @Autowired
    private PaymentsRepository paymentsRepository;

    // Lấy tất cả báo cáo doanh thu
    public List<RevenueReportDTO> getAllReports() {
        return revenueReportRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Lấy báo cáo theo ID
    public Optional<RevenueReportDTO> getReportById(Integer id) {
        return revenueReportRepository.findById(id).map(this::convertToDTO);
    }

    // Lấy báo cáo theo năm (dùng stored procedure)
    public List<RevenueReportDTO> getReportsByYear(Integer year) {
        List<Object[]> results = revenueReportRepository.getRevenueReportNative(year);
        return results.stream().map(row -> {
            RevenueReportDTO dto = new RevenueReportDTO();
            dto.setReportMonth((Integer) row[0]);
            dto.setTotalRevenue(((java.math.BigDecimal) row[1]).doubleValue());
            dto.setOrderCount((Integer) row[2]);
            dto.setReportYear(year);
            return dto;
        }).collect(Collectors.toList());
    }

    // Lấy báo cáo theo tháng và năm
    public Optional<RevenueReportDTO> getReportByMonthAndYear(Integer month, Integer year) {
        return revenueReportRepository.findByReportMonthAndReportYear(month, year).map(this::convertToDTO);
    }

    // Tạo báo cáo doanh thu tháng (dùng stored procedure)
    public RevenueReportDTO generateMonthlyReport(Integer month, Integer year) {
        revenueReportRepository.generateMonthlyRevenueReport(month, year);

        // Lấy báo cáo vừa tạo
        RevenueReport report = revenueReportRepository.findByReportMonthAndReportYear(month, year)
                .orElseThrow(() -> new RuntimeException("Không thể tạo báo cáo!"));
        return convertToDTO(report);
    }

    // Tạo báo cáo doanh thu tháng thủ công (không dùng stored procedure)
    public RevenueReportDTO generateMonthlyReportManually(Integer month, Integer year) {
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1).minusSeconds(1);

        Double totalRevenue = paymentsRepository.getTotalRevenueBetweenDates(startDate, endDate);
        long orderCount = paymentsRepository.findAll().stream()
                .filter(p -> p.getPaymentDate().isAfter(startDate) && p.getPaymentDate().isBefore(endDate))
                .map(p -> p.getOrder().getOrderId())
                .distinct()
                .count();

        RevenueReport report = new RevenueReport();
        report.setReportMonth(month);
        report.setReportYear(year);
        report.setTotalRevenue(totalRevenue != null ? totalRevenue : 0.0);
        report.setOrderCount((int) orderCount);

        RevenueReport savedReport = revenueReportRepository.save(report);
        return convertToDTO(savedReport);
    }

    // Xóa báo cáo
    public void deleteReport(Integer id) {
        RevenueReport report = revenueReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy báo cáo!"));
        revenueReportRepository.delete(report);
    }

    // Chuyển từ Entity sang DTO
    private RevenueReportDTO convertToDTO(RevenueReport report) {
        RevenueReportDTO dto = new RevenueReportDTO();
        dto.setReportId(report.getReportId());
        dto.setReportMonth(report.getReportMonth());
        dto.setReportYear(report.getReportYear());
        dto.setTotalRevenue(report.getTotalRevenue());
        dto.setOrderCount(report.getOrderCount());
        dto.setGeneratedDate(report.getGeneratedDate());
        return dto;
    }
}