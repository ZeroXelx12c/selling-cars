package com.example.selling_cars.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "revenue_reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Integer reportId;
    
    @Column(name = "report_month", nullable = false)
    private Integer reportMonth;
    
    @Column(name = "report_year", nullable = false)
    private Integer reportYear;
    
    @Column(name = "generated_date", nullable = false)
    private LocalDateTime generatedDate;
    
    @Column(name = "total_revenue", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalRevenue;
    
    @Column(name = "order_count", nullable = false)
    private Integer orderCount;
    
    @Column(name = "average_order_value", precision = 12, scale = 2)
    private BigDecimal averageOrderValue;
    
    @Column(name = "top_selling_products", columnDefinition = "TEXT")
    private String topSellingProducts;
    
    @Column(name = "revenue_by_category", columnDefinition = "TEXT")
    private String revenueByCategory;
} 