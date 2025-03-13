package com.example.selling_cars.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "News")
@Data
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer newsId;

    @ManyToOne
    @JoinColumn(name = "CategoryID", nullable = false)
    private Categories category;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String content;

    @Column(length = 255)
    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime publishedDate = LocalDateTime.now();
}