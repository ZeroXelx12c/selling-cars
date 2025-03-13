package com.example.selling_cars.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Integer userId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;
    private String role;
    private boolean rememberMe;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    
    private String socialProvider;
    private String socialId;
    private LocalDateTime createdAt;

    // Chức năng bổ sung
    private int orderCount; // Số đơn hàng của người dùng
    private double totalSpending; // Tổng chi tiêu
}