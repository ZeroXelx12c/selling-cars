package com.example.selling_cars.dto;

import lombok.Data;

@Data
public class ProductOptionDTO {
    private Integer optionId;
    private String optionType; // Exterior, Interior
    private String optionName;
    private Double additionalPrice;
}