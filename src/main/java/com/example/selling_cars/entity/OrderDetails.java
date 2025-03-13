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
    private Integer quantity = 1; // Mặc định là 1 với ô tô

    @Column(nullable = false)
    private Double unitPrice;
}