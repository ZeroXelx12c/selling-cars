package com.example.selling_cars.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RevenueReportDTO {
    private Integer reportId;
    private Integer reportMonth;
    private Integer reportYear;
    private Double totalRevenue;
    private Integer orderCount;
    private LocalDateTime generatedDate;
}