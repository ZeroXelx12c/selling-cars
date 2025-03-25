package com.example.selling_cars.repository;

import com.example.selling_cars.entity.RevenueReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RevenueReportRepository extends JpaRepository<RevenueReport, Integer> {
    // Tìm báo cáo theo tháng và năm
    Optional<RevenueReport> findByReportMonthAndReportYear(Integer month, Integer year);

    // Tìm báo cáo theo năm
    List<RevenueReport> findByReportYear(Integer year);

    // Tìm báo cáo theo khoảng thời gian
    List<RevenueReport> findByGeneratedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Tìm báo cáo theo tổng doanh thu
    List<RevenueReport> findByTotalRevenueGreaterThanEqual(BigDecimal minRevenue);

    // Tìm báo cáo theo số lượng đơn hàng
    List<RevenueReport> findByOrderCountGreaterThanEqual(Integer minOrders);

    // Tìm báo cáo mới nhất
    Optional<RevenueReport> findTopByOrderByGeneratedDateDesc();

    // Tìm báo cáo với giá trị đơn hàng trung bình từ một giá trị trở lên
    List<RevenueReport> findByAverageOrderValueGreaterThanEqual(BigDecimal minAverage);

    // Lấy danh sách báo cáo sắp xếp theo thời gian
    List<RevenueReport> findAllByOrderByGeneratedDateDesc();

    // Đếm số lượng báo cáo theo năm
    long countByReportYear(Integer year);

    // Đếm số lượng báo cáo theo khoảng thời gian
    long countByGeneratedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Tính tổng doanh thu theo năm
    @Query("SELECT SUM(r.totalRevenue) FROM RevenueReport r WHERE r.reportYear = :year")
    BigDecimal calculateTotalRevenueByYear(Integer year);

    // Tính tổng số đơn hàng theo năm
    @Query("SELECT SUM(r.orderCount) FROM RevenueReport r WHERE r.reportYear = :year")
    Integer calculateTotalOrdersByYear(Integer year);
} 