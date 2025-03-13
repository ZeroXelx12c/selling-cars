package com.example.selling_cars.repository;

import com.example.selling_cars.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    // Lấy đơn hàng theo người dùng
    List<Orders> findByUserUserId(Integer userId);

    // Lấy đơn hàng theo trạng thái
    List<Orders> findByOrderStatus(String orderStatus);

    // Đếm đơn hàng mới trong ngày
    @Query("SELECT COUNT(o) FROM Orders o WHERE o.orderDate >= :startOfDay AND o.orderStatus = 'Pending'")
    long countNewOrdersToday(LocalDateTime startOfDay);

    // Gọi stored procedure CreateOrder
    @Query(value = "EXEC CreateOrder @UserID = :userId, @ProductID = :productId, @ExteriorOptionID = :exteriorOptionId, " +
            "@InteriorOptionID = :interiorOptionId, @DeliveryArea = :deliveryArea, @DepositPercentage = :depositPercentage",
            nativeQuery = true)
    void createOrder(
            Integer userId,
            Integer productId,
            Integer exteriorOptionId,
            Integer interiorOptionId,
            String deliveryArea,
            Double depositPercentage
    );

    // Gọi stored procedure RecordPayment
    @Query(value = "EXEC RecordPayment @OrderID = :orderId, @Amount = :amount, @PaymentMethod = :paymentMethod, " +
            "@TransactionID = :transactionId", nativeQuery = true)
    void recordPayment(Integer orderId, Double amount, String paymentMethod, String transactionId);
}