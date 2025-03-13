package com.example.selling_cars.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "OrderDetails")
@Data
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDetailId;

    @ManyToOne
    @JoinColumn(name = "OrderID", nullable = false)
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Products product;

    @Column(nullable = false)
    private Integer quantity = 1;

    @Column(nullable = false)
    private Double unitPrice;

    @ManyToOne
    @JoinColumn(name = "ExteriorOptionID")
    private ProductOptions exteriorOption;

    @ManyToOne
    @JoinColumn(name = "InteriorOptionID")
    private ProductOptions interiorOption;
}