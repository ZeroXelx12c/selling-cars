package com.example.selling_cars.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsDTO {
    private Integer newsId;

    @NotBlank(message = "Title is required")
    @Size(max = 200)
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @Size(max = 500)
    private String summary;

    @Size(max = 255)
    private String imageUrl;

    @NotNull(message = "Published date is required")
    private LocalDateTime publishedDate;

    @NotBlank(message = "Status is required")
    @Size(max = 20)
    private String status;

    private Integer categoryId;
} 