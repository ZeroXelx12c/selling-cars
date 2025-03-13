package com.example.selling_cars.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Orders")
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private Users user;

    @Column(nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false, length = 50)
    private String paymentMethod;

    @Column(length = 20)
    private String paymentStatus = "Pending";

    @Column(length = 20)
    private String orderStatus = "Processing";

    @Column(length = 255)
    private String notes;

    // Quan hệ 1-n với OrderDetails
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetails> orderDetails;
}