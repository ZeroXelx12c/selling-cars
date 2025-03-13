package com.example.selling_cars.repository;

import com.example.selling_cars.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    // Lấy đơn hàng theo user
    List<Orders> findByUserUserId(Integer userId);

    // Đếm đơn hàng mới trong ngày
    @Query("SELECT COUNT(o) FROM Orders o WHERE o.orderDate >= :startOfDay AND o.orderStatus = 'Processing'")
    long countNewOrdersToday(@Param("startOfDay") LocalDateTime startOfDay);

    // Lấy đơn hàng theo trạng thái
    List<Orders> findByOrderStatus(String orderStatus);

    // Lấy tổng doanh thu từ các đơn hàng đã thanh toán
    @Query("SELECT SUM(o.totalAmount) FROM Orders o WHERE o.paymentStatus = 'Completed'")
    Double getTotalRevenue();

    @Query(value = "EXEC GetOrderDetails @OrderID = :orderId", nativeQuery = true)
    List<Object[]> getOrderDetailsNative(@Param("orderId") Integer orderId);
}