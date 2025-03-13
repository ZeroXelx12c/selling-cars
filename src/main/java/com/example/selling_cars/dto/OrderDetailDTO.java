package com.example.selling_cars.dto;

import lombok.Data;

@Data
public class OrderDetailDTO {
    private Integer orderDetailId;
    private Integer orderId;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private Double unitPrice;
    private Integer exteriorOptionId;
    private String exteriorOptionName;
    private Integer interiorOptionId;
    private String interiorOptionName;
}