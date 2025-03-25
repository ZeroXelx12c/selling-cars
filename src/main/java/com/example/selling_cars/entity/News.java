package com.example.selling_cars.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Data
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Integer newsId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotBlank(message = "Tiêu đề không được để trống!")
    @Size(max = 200, message = "Tiêu đề không được vượt quá 200 ký tự!")
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @NotBlank(message = "Nội dung không được để trống!")
    @Column(name = "content", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String content;

    @Size(max = 255, message = "URL hình ảnh không được vượt quá 255 ký tự!")
    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "published_date")
    private LocalDateTime publishedDate = LocalDateTime.now();

    @Size(max = 500)
    @Column(name = "summary")
    private String summary;

    @NotBlank(message = "Status is required")
    @Size(max = 20)
    @Column(name = "status")
    private String status;
} 