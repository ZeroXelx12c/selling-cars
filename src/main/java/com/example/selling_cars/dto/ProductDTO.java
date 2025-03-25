package com.example.selling_cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    
    private Integer productId;
    
    @NotBlank(message = "Product name is required")
    @Size(max = 200, message = "Product name must be less than 200 characters")
    private String productName;
    
    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private BigDecimal price;
    
    private String imageUrl;
    
    @NotBlank(message = "Model is required")
    private String model;
    
    @NotNull(message = "Manufacture year is required")
    @Min(value = 1900, message = "Manufacture year must be greater than or equal to 1900")
    private Integer manufactureYear;
    
    @NotBlank(message = "Engine details are required")
    private String engine;
    
    @Min(value = 0, message = "Mileage must be greater than or equal to 0")
    private Integer mileage;
    
    @NotBlank(message = "Status is required")
    private String status;
    
    @Size(max = 4000, message = "Description must be less than 4000 characters")
    private String description;
    
    private LocalDateTime createdAt;
    
    @NotNull(message = "Category ID is required")
    private Integer categoryId;
} 