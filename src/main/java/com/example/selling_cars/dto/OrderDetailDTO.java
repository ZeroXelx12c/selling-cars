package com.example.selling_cars.dto;

import lombok.Data;

@Data
public class OrderDetailDTO {
    private Integer orderDetailId;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private Double unitPrice;
    private String exteriorOptionName;
    private String interiorOptionName;

    // Chức năng bổ sung
    private Double exteriorAdditionalPrice;
    private Double interiorAdditionalPrice;
}