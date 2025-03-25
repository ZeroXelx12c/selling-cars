package com.example.selling_cars.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product_options")
@Data
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Integer optionId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotBlank(message = "Loại tùy chọn không được để trống!")
    @Size(max = 20, message = "Loại tùy chọn không được vượt quá 20 ký tự!")
    @Column(name = "option_type", nullable = false, length = 20)
    private String optionType;

    @NotBlank(message = "Tên tùy chọn không được để trống!")
    @Size(max = 50, message = "Tên tùy chọn không được vượt quá 50 ký tự!")
    @Column(name = "option_name", nullable = false, length = 50)
    private String optionName;

    @DecimalMin(value = "0.0", message = "Giá bổ sung phải lớn hơn hoặc bằng 0!")
    @Column(name = "additional_price", precision = 18, scale = 2)
    private BigDecimal additionalPrice = BigDecimal.ZERO;

    @Size(max = 500)
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "exteriorOption")
    private List<OrderDetail> exteriorOrderDetails;

    @OneToMany(mappedBy = "interiorOption")
    private List<OrderDetail> interiorOrderDetails;
} 