package com.example.selling_cars.repository;

import com.example.selling_cars.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Tìm đơn hàng theo người dùng
    List<Order> findByUserUserId(Integer userId);

    // Tìm đơn hàng theo trạng thái
    List<Order> findByOrderStatus(String status);

    // Tìm đơn hàng theo người dùng và trạng thái
    List<Order> findByUserUserIdAndOrderStatus(Integer userId, String status);

    // Tìm đơn hàng theo khoảng thời gian
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Tìm đơn hàng theo khoảng giá
    List<Order> findByTotalAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);

    // Tìm đơn hàng theo khu vực giao hàng
    List<Order> findByDeliveryArea(String deliveryArea);

    // Tìm đơn hàng theo nhiều tiêu chí
    @Query("SELECT o FROM Order o WHERE " +
           "(:userId IS NULL OR o.user.userId = :userId) AND " +
           "(:status IS NULL OR o.orderStatus = :status) AND " +
           "(:startDate IS NULL OR o.orderDate >= :startDate) AND " +
           "(:endDate IS NULL OR o.orderDate <= :endDate) AND " +
           "(:minAmount IS NULL OR o.totalAmount >= :minAmount) AND " +
           "(:maxAmount IS NULL OR o.totalAmount <= :maxAmount) AND " +
           "(:deliveryArea IS NULL OR o.deliveryArea = :deliveryArea)")
    Page<Order> findByFilters(Integer userId, String status, LocalDateTime startDate,
                             LocalDateTime endDate, BigDecimal minAmount, BigDecimal maxAmount,
                             String deliveryArea, Pageable pageable);

    // Đếm số lượng đơn hàng theo trạng thái
    long countByOrderStatus(String status);

    // Đếm số lượng đơn hàng theo người dùng
    long countByUserUserId(Integer userId);

    // Tính tổng doanh thu theo khoảng thời gian
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    BigDecimal calculateTotalRevenue(LocalDateTime startDate, LocalDateTime endDate);

    // Tính tổng doanh thu theo trạng thái
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderStatus = :status")
    BigDecimal calculateTotalRevenueByStatus(String status);
} 