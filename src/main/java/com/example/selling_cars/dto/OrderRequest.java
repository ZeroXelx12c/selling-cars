package com.example.selling_cars.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private Integer productId;
    private Integer exteriorOptionId;
    private Integer interiorOptionId;
    private String customerName;
    private String phoneNumber;
    private String email;
    private String deliveryArea;
    private Integer depositPercent;
}