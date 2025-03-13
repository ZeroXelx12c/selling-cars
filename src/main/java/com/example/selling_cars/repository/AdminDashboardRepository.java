package com.example.selling_cars.repository;

import com.example.selling_cars.entity.AdminDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AdminDashboardRepository extends JpaRepository<AdminDashboard, Integer> {
    // Lấy bản ghi tổng quan mới nhất
    Optional<AdminDashboard> findTopByOrderByReportDateDesc();

    // Lấy bản ghi theo ngày cụ thể
    Optional<AdminDashboard> findByReportDate(LocalDate reportDate);

    // Gọi stored procedure UpdateAdminDashboard
    @Query(value = "EXEC UpdateAdminDashboard", nativeQuery = true)
    void updateAdminDashboard();
}