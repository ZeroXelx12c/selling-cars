package com.example.selling_cars.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDTO {
    private Integer productId;
    private Integer categoryId;
    private String categoryName;
    private String productName;
    private Double price;
    private String imageUrl;
    private String model;
    private Integer manufactureYear;
    private String engine;
    private Integer mileage;
    private String status;
    private String description;
    private LocalDateTime createdAt;

    // Chức năng bổ sung
    private List<ProductOptionDTO> options;
    private boolean isFeatured;
}