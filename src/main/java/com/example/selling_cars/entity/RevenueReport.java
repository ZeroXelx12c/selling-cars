package com.example.selling_cars.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "RevenueReport")
@Data
public class RevenueReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;

    @Column(nullable = false)
    private Integer reportMonth; // 1-12

    @Column(nullable = false)
    private Integer reportYear;

    @Column(nullable = false)
    private Double totalRevenue;

    @Column(nullable = false)
    private Integer orderCount;

    @Column(nullable = false)
    private LocalDateTime generatedDate = LocalDateTime.now();
}