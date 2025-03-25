package com.example.selling_cars.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
@Data
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Integer orderDetailId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull(message = "Số lượng không được để trống!")
    @Min(value = 1, message = "Số lượng phải lớn hơn hoặc bằng 1!")
    @Column(name = "quantity", nullable = false)
    private Integer quantity = 1;

    @NotNull(message = "Đơn giá không được để trống!")
    @DecimalMin(value = "0.0", message = "Đơn giá phải lớn hơn hoặc bằng 0!")
    @Column(name = "unit_price", nullable = false, precision = 18, scale = 2)
    private BigDecimal unitPrice;

    @ManyToOne
    @JoinColumn(name = "exterior_option_id")
    private ProductOption exteriorOption;

    @ManyToOne
    @JoinColumn(name = "interior_option_id")
    private ProductOption interiorOption;
} 