package com.example.selling_cars.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ProductOptions")
@Data
public class ProductOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer optionId;

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Products product;

    @Column(nullable = false, length = 20)
    private String optionType; // "Exterior" hoặc "Interior"

    @Column(nullable = false, length = 50)
    private String optionName;

    @Column(nullable = false)
    private Double additionalPrice = 0.0;
}