package com.example.selling_cars.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class RevenueReportDTO {
    private Integer reportId;

    @NotNull(message = "Report month is required")
    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")
    private Integer reportMonth;

    @NotNull(message = "Report year is required")
    @Min(value = 2000, message = "Year must be greater than or equal to 2000")
    @Max(value = 2100, message = "Year must be less than or equal to 2100")
    private Integer reportYear;

    @NotNull(message = "Generated date is required")
    private LocalDateTime generatedDate;

    @NotNull(message = "Total revenue is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total revenue must be greater than 0")
    private BigDecimal totalRevenue;

    @NotNull(message = "Order count is required")
    @Min(value = 0, message = "Order count must be greater than or equal to 0")
    private Integer orderCount;

    @NotNull(message = "Average order value is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Average order value must be greater than 0")
    private BigDecimal averageOrderValue;

    private Map<String, Integer> topSellingProducts;
    private Map<String, BigDecimal> revenueByCategory;
} 