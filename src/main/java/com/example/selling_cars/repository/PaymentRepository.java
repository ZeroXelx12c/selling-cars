package com.example.selling_cars.repository;

import com.example.selling_cars.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    // Tìm thanh toán theo đơn hàng
    List<Payment> findByOrderOrderId(Integer orderId);

    // Tìm thanh toán theo phương thức thanh toán
    List<Payment> findByPaymentMethod(String paymentMethod);

    // Tìm thanh toán theo khoảng thời gian
    List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Tìm thanh toán theo ID giao dịch
    Optional<Payment> findByTransactionId(String transactionId);

    // Tìm thanh toán theo đơn hàng và phương thức thanh toán
    List<Payment> findByOrderOrderIdAndPaymentMethod(Integer orderId, String paymentMethod);

    // Tìm thanh toán theo đơn hàng và khoảng thời gian
    List<Payment> findByOrderOrderIdAndPaymentDateBetween(Integer orderId, LocalDateTime startDate, LocalDateTime endDate);

    // Đếm số lượng thanh toán theo phương thức
    long countByPaymentMethod(String paymentMethod);

    // Đếm số lượng thanh toán theo đơn hàng
    long countByOrderOrderId(Integer orderId);

    // Kiểm tra thanh toán có tồn tại theo ID giao dịch
    boolean existsByTransactionId(String transactionId);
} 