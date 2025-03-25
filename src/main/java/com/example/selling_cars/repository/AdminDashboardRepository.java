package com.example.selling_cars.repository;

import com.example.selling_cars.entity.AdminDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminDashboardRepository extends JpaRepository<AdminDashboard, Integer> {
    // Tìm báo cáo theo ngày
    Optional<AdminDashboard> findByReportDate(LocalDate reportDate);

    // Tìm báo cáo theo khoảng thời gian
    List<AdminDashboard> findByReportDateBetween(LocalDate startDate, LocalDate endDate);

    // Tìm báo cáo mới nhất
    Optional<AdminDashboard> findFirstByOrderByReportDateDesc();

    // Tìm báo cáo theo tổng doanh thu
    List<AdminDashboard> findByTotalRevenueGreaterThanEqual(BigDecimal minRevenue);

    // Tìm báo cáo theo số đơn hàng mới
    List<AdminDashboard> findByNewOrdersGreaterThanEqual(Integer minOrders);

    // Tìm báo cáo theo số lượng tồn kho
    List<AdminDashboard> findByInventoryCountLessThanEqual(Integer maxInventory);

    // Tìm báo cáo theo tổng số khách hàng
    List<AdminDashboard> findByTotalCustomersGreaterThanEqual(Integer minCustomers);

    // Lấy danh sách báo cáo sắp xếp theo ngày
    List<AdminDashboard> findAllByOrderByReportDateDesc();

    // Đếm số lượng báo cáo theo khoảng thời gian
    long countByReportDateBetween(LocalDate startDate, LocalDate endDate);
} 