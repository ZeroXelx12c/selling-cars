package com.example.selling_cars.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "admin_dashboard")
@Data
public class AdminDashboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dashboard_id")
    private Integer dashboardId;

    @NotNull(message = "Tổng doanh thu không được để trống!")
    @DecimalMin(value = "0.0", message = "Tổng doanh thu phải lớn hơn hoặc bằng 0!")
    @Column(name = "total_revenue", nullable = false, precision = 18, scale = 2)
    private BigDecimal totalRevenue;

    @NotNull(message = "Số đơn hàng mới không được để trống!")
    @Min(value = 0, message = "Số đơn hàng mới không được âm!")
    @Column(name = "new_orders", nullable = false)
    private Integer newOrders;

    @NotNull(message = "Tổng số khách hàng không được để trống!")
    @Min(value = 0, message = "Tổng số khách hàng không được âm!")
    @Column(name = "total_customers", nullable = false)
    private Integer totalCustomers;

    @NotNull(message = "Số lượng tồn kho không được để trống!")
    @Min(value = 0, message = "Số lượng tồn kho không được âm!")
    @Column(name = "inventory_count", nullable = false)
    private Integer inventoryCount;

    @Column(name = "report_date")
    private LocalDate reportDate = LocalDate.now();
} 