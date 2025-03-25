package com.example.selling_cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
    
    private Integer orderDetailId;
    
    @NotNull(message = "Order ID is required")
    private Integer orderId;
    
    @NotNull(message = "Product ID is required")
    private Integer productId;
    
    private Integer exteriorOptionId;
    
    private Integer interiorOptionId;
    
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    
    @NotNull(message = "Unit price is required")
    @Min(value = 0, message = "Unit price must be greater than or equal to 0")
    private BigDecimal unitPrice;
    
    @NotNull(message = "Total price is required")
    @Min(value = 0, message = "Total price must be greater than or equal to 0")
    private BigDecimal totalPrice;
} 