package com.example.selling_cars.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @NotNull(message = "Order date is required")
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than 0")
    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @NotNull(message = "Số tiền đặt cọc không được để trống!")
    @DecimalMin(value = "0.0", message = "Số tiền đặt cọc phải lớn hơn hoặc bằng 0!")
    @Column(name = "deposit_amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal depositAmount;

    @NotBlank(message = "Delivery address is required")
    @Size(max = 255)
    @Column(name = "delivery_address")
    private String deliveryAddress;

    @NotBlank(message = "Delivery area is required")
    @Size(max = 100)
    @Column(name = "delivery_area")
    private String deliveryArea;

    @NotBlank(message = "Delivery phone is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number format")
    @Column(name = "delivery_phone")
    private String deliveryPhone;

    @NotBlank(message = "Order status is required")
    @Size(max = 20)
    @Column(name = "order_status")
    private String orderStatus;

    @Size(max = 255, message = "Ghi chú không được vượt quá 255 ký tự!")
    @Column(name = "notes", length = 255)
    private String notes;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Payment> payments;
} 