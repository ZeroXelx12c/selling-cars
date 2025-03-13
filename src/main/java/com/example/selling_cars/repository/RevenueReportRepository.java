package com.example.selling_cars.repository;

import com.example.selling_cars.entity.RevenueReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RevenueReportRepository extends JpaRepository<RevenueReport, Integer> {
    // Lấy báo cáo theo năm
    List<RevenueReport> findByReportYearOrderByReportMonthAsc(Integer reportYear);

    // Lấy báo cáo theo tháng và năm
    Optional<RevenueReport> findByReportMonthAndReportYear(Integer reportMonth, Integer reportYear);

    // Gọi stored procedure GenerateMonthlyRevenueReport
    @Query(value = "EXEC GenerateMonthlyRevenueReport @Month = :month, @Year = :year", nativeQuery = true)
    void generateMonthlyRevenueReport(Integer month, Integer year);

    // Gọi stored procedure GetRevenueReport
    @Query(value = "EXEC GetRevenueReport @Year = :year", nativeQuery = true)
    List<Object[]> getRevenueReportNative(Integer year);
}