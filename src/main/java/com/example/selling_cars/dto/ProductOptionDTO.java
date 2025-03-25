package com.example.selling_cars.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductOptionDTO {
    private Integer optionId;

    @NotBlank(message = "Option name is required")
    @Size(max = 100)
    private String optionName;

    @NotBlank(message = "Option type is required")
    @Size(max = 20)
    private String optionType;

    @Size(max = 500)
    private String description;

    @NotNull(message = "Additional price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Additional price must be greater than 0")
    private BigDecimal additionalPrice;

    private Integer productId;
} 