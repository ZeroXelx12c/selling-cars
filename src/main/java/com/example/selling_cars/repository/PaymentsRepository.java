package com.example.selling_cars.repository;

import com.example.selling_cars.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Integer> {
    // Lấy thanh toán theo đơn hàng
    Payments findByOrderOrderId(Integer orderId);

    // Lấy danh sách thanh toán theo người dùng (qua đơn hàng)
    @Query("SELECT p FROM Payments p WHERE p.order.user.userId = :userId")
    List<Payments> findByUserId(Integer userId);

    // Tính tổng doanh thu trong khoảng thời gian
    @Query("SELECT SUM(p.amount) FROM Payments p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    Double getTotalRevenueBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    // Đếm số giao dịch trong ngày
    @Query("SELECT COUNT(p) FROM Payments p WHERE p.paymentDate >= :startOfDay")
    long countPaymentsToday(LocalDateTime startOfDay);
}