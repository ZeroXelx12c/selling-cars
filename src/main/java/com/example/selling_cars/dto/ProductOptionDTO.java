package com.example.selling_cars.dto;

import lombok.Data;

@Data
public class ProductOptionDTO {
    private Integer optionId;
    private Integer productId;
    private String optionType; // "Exterior" hoặc "Interior"
    private String optionName;
    private Double additionalPrice;
}