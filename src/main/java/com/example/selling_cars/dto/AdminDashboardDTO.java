package com.example.selling_cars.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AdminDashboardDTO {
    private Integer dashboardId;
    private Double totalRevenue;
    private Integer newOrders;
    private Integer totalCustomers;
    private Integer inventoryCount;
    private LocalDate reportDate;
}