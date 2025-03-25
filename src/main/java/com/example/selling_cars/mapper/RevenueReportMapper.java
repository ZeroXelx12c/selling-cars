package com.example.selling_cars.mapper;

import com.example.selling_cars.dto.RevenueReportDTO;
import com.example.selling_cars.entity.RevenueReport;
import org.springframework.stereotype.Component;

@Component
public class RevenueReportMapper {
    
    public RevenueReportDTO toDTO(RevenueReport report) {
        if (report == null) {
            return null;
        }
        
        RevenueReportDTO dto = new RevenueReportDTO();
        dto.setReportId(report.getReportId());
        dto.setReportMonth(report.getReportMonth());
        dto.setReportYear(report.getReportYear());
        dto.setGeneratedDate(report.getGeneratedDate());
        dto.setTotalRevenue(report.getTotalRevenue());
        dto.setOrderCount(report.getOrderCount());
        
        // Calculate average order value
        if (report.getOrderCount() > 0) {
            dto.setAverageOrderValue(report.getTotalRevenue().divide(
                    java.math.BigDecimal.valueOf(report.getOrderCount()), 2, java.math.RoundingMode.HALF_UP));
        }
        
        return dto;
    }

    public RevenueReport toEntity(RevenueReportDTO dto) {
        if (dto == null) {
            return null;
        }
        
        RevenueReport report = new RevenueReport();
        report.setReportId(dto.getReportId());
        report.setReportMonth(dto.getReportMonth());
        report.setReportYear(dto.getReportYear());
        report.setGeneratedDate(dto.getGeneratedDate());
        report.setTotalRevenue(dto.getTotalRevenue());
        report.setOrderCount(dto.getOrderCount());
        
        return report;
    }
} 