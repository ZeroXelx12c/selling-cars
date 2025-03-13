package com.example.selling_cars.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    private Integer paymentId;
    private Integer orderId;
    private Double amount;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private String transactionId;
}