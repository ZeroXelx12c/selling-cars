package com.example.selling_cars.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Products")
@Data
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @ManyToOne
    @JoinColumn(name = "CategoryID", nullable = false)
    private Categories category;

    @Column(nullable = false, length = 100)
    private String productName;

    @Column(nullable = false)
    private Double price;

    @Column(length = 255)
    private String imageUrl;

    @Column(length = 50)
    private String model;

    @Column
    private Integer manufactureYear;

    @Column(length = 30)
    private String engine;

    @Column(nullable = false)
    private Integer mileage = 0;

    @Column(nullable = false, length = 20)
    private String status = "InStock";

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Quan hệ 1-n với ProductOptions
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductOptions> options;
}