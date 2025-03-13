package com.example.selling_cars.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Data // Lombok tự động tạo getter, setter, toString...
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(length = 255)
    private String password;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 15)
    private String phoneNumber;

    @Column(length = 255)
    private String address;

    @Column(nullable = false, length = 20)
    private String role = "Customer"; // Giá trị mặc định là Customer

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(length = 50)
    private String socialLoginProvider;

    @Column(length = 255)
    private String socialLoginId;
}