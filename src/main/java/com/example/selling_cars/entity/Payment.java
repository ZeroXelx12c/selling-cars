package com.example.selling_cars.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @NotNull(message = "Số tiền không được để trống!")
    @DecimalMin(value = "0.0", message = "Số tiền phải lớn hơn hoặc bằng 0!")
    @Column(name = "amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate = LocalDateTime.now();

    @NotBlank(message = "Phương thức thanh toán không được để trống!")
    @Size(max = 50, message = "Phương thức thanh toán không được vượt quá 50 ký tự!")
    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;

    @Size(max = 100, message = "ID giao dịch không được vượt quá 100 ký tự!")
    @Column(name = "transaction_id", length = 100)
    private String transactionId;

    @NotBlank(message = "Payment status is required")
    @Size(max = 20)
    @Column(name = "payment_status")
    private String paymentStatus;
} 