package com.example.selling_cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    
    private Integer orderId;
    
    @NotNull(message = "User ID is required")
    private Integer userId;
    
    private LocalDateTime orderDate;
    
    @NotBlank(message = "Order status is required")
    private String orderStatus;
    
    @NotBlank(message = "Delivery address is required")
    @Size(max = 500, message = "Delivery address must be less than 500 characters")
    private String deliveryAddress;
    
    @NotBlank(message = "Delivery area is required")
    private String deliveryArea;
    
    @NotBlank(message = "Delivery phone is required")
    @Size(min = 10, max = 15, message = "Delivery phone must be between 10 and 15 characters")
    private String deliveryPhone;
    
    @NotNull(message = "Total amount is required")
    @Min(value = 0, message = "Total amount must be greater than or equal to 0")
    private BigDecimal totalAmount;
    
    private List<OrderDetailDTO> orderDetails;
} 