package com.example.selling_cars.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDTO {
    private Integer categoryId;

    @NotBlank(message = "Category name is required")
    @Size(max = 100)
    private String categoryName;

    @Size(max = 500)
    private String description;

    @Size(max = 255)
    private String imageUrl;

    @Size(max = 20)
    private String status;
} 