package com.example.selling_cars.repository;

import com.example.selling_cars.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Integer> {
    // Lấy thanh toán theo OrderID
    Payments findByOrderOrderId(Integer orderId);

    // Lấy danh sách thanh toán theo phương thức thanh toán
    List<Payments> findByPaymentMethod(String paymentMethod);

    // Tính tổng doanh thu theo tháng
    @Query("SELECT SUM(p.amount) FROM Payments p WHERE MONTH(p.paymentDate) = :month AND YEAR(p.paymentDate) = :year")
    Double getMonthlyRevenue(@Param("month") int month, @Param("year") int year);

    // Lấy danh sách thanh toán trong khoảng thời gian
    List<Payments> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}