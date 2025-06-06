package com.example.selling_cars.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotBlank(message = "Tên Sản Phẩm không được để trống!")
    @Size(max = 100, message = "Tên Sản Phẩm không được vượt quá 100 ký tự!")
    @Column(name = "product_name", nullable = false, length = 100, unique = true)
    private String productName;

    @NotNull(message = "Giá không được để trống!")
    @DecimalMin(value = "0.0", message = "Giá phải lớn hơn hoặc bằng 0!")
    @DecimalMax(value = "9999999999999999.99", message = "Giá vượt quá giới hạn cho phép!") // Giới hạn cho DECIMAL(18,2)
    @Column(name = "price", nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    @Column(name = "image_url", columnDefinition = "NVARCHAR(MAX)")
    private String imageUrl;

    @Size(max = 50, message = "Model không được vượt quá 50 ký tự!")
    @Column(name = "model", length = 50)
    private String model;

    @Min(value = 1900, message = "Năm sản xuất không hợp lệ!")
    @Max(value = 2100, message = "Năm sản xuất không hợp lệ!")
    @Column(name = "manufacture_year")
    private Integer manufactureYear;

    @Size(max = 30, message = "Thông tin động cơ không được vượt quá 30 ký tự!")
    @Column(name = "engine", length = 30)
    private String engine;

    @Min(value = 0, message = "Số km đã chạy không được âm!")
    @Max(value = 2147483647, message = "Số km đã chạy vượt quá giới hạn cho phép!") // Giới hạn INT
    @Column(name = "mileage")
    private Integer mileage = 0;

    @Size(max = 20, message = "Trạng thái không được vượt quá 20 ký tự!")
    @Column(name = "status", length = 20)
    private String status = "in_stock";

    @Size(max = 1000, message = "Description không được vượt quá 1000 ký tự!")
    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "product")
    private List<ProductOption> options;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;
}