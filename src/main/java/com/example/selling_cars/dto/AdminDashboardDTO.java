package com.example.selling_cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardDTO {
    
    private Integer reportId;
    
    @NotNull(message = "Report date is required")
    private LocalDate reportDate;
    
    @NotNull(message = "Total revenue is required")
    @Min(value = 0, message = "Total revenue must be greater than or equal to 0")
    private BigDecimal totalRevenue;
    
    @NotNull(message = "New orders count is required")
    @Min(value = 0, message = "New orders count must be greater than or equal to 0")
    private Integer newOrders;
    
    @NotNull(message = "Inventory count is required")
    @Min(value = 0, message = "Inventory count must be greater than or equal to 0")
    private Integer inventoryCount;
    
    @NotNull(message = "Total customers count is required")
    @Min(value = 0, message = "Total customers count must be greater than or equal to 0")
    private Integer totalCustomers;
} 