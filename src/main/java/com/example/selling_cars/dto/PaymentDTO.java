package com.example.selling_cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    
    private Integer paymentId;
    
    @NotNull(message = "Order ID is required")
    private Integer orderId;
    
    @NotBlank(message = "Transaction ID is required")
    private String transactionId;
    
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
    
    private LocalDateTime paymentDate;
    
    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    private BigDecimal amount;
    
    @NotBlank(message = "Payment status is required")
    private String paymentStatus;
} 