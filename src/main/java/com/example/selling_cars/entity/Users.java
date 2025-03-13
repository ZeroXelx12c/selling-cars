package com.example.selling_cars.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, unique = true, length = 15)
    private String phoneNumber;

    @Column(unique = true, length = 100)
    private String email;

    @Column
    private LocalDate dateOfBirth;

    @Column(length = 255)
    private String password;

    @Column(nullable = false, length = 20)
    private String role = "Customer";

    @Column(nullable = false)
    private Boolean rememberMe = false;

    @Column(length = 50)
    private String socialProvider;

    @Column(length = 255)
    private String socialId;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}