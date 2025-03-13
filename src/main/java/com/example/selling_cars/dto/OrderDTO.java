package com.example.selling_cars.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Integer orderId;
    private Integer userId;
    private String userFullName;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private Double depositAmount;
    private String deliveryArea;
    private String orderStatus;
    private String notes;

    // Chức năng bổ sung
    private List<OrderDetailDTO> orderDetails;
    private String paymentStatus;
    private String transactionId;
}