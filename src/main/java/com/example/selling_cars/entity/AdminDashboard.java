package com.example.selling_cars.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "AdminDashboard")
@Data
public class AdminDashboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dashboardId;

    @Column(nullable = false)
    private Double totalRevenue;

    @Column(nullable = false)
    private Integer newOrders;

    @Column(nullable = false)
    private Integer totalCustomers;

    @Column(nullable = false)
    private Integer inventoryCount;

    @Column(nullable = false)
    private LocalDate reportDate = LocalDate.now();
}