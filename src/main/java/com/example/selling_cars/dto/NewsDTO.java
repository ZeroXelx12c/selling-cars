package com.example.selling_cars.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsDTO {
    private Integer newsId;
    private Integer categoryId;
    private String categoryName;
    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime publishedDate;
}