package com.example.selling_cars.entity;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "categories")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @NotBlank(message = "Tên danh mục không được để trống!")
    @Size(max = 100, message = "Tên danh mục không được vượt quá 100 ký tự!")
    @Column(name = "category_name", nullable = false, length = 100, unique = true)
    private String categoryName;

    @Size(max = 500, message = "Mô tả không được vượt quá 500 ký tự!")
    @Column(name = "description", length = 500)
    private String description;

    @Size(max = 255, message = "Mô tả không được vượt quá 255 ký tự!")
    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Size(max = 20, message = "Trạng thái không được vượt quá 20 ký tự!")
    @Column(name = "status", length = 20)
    private String status;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @OneToMany(mappedBy = "category")
    private List<News> news;
} 