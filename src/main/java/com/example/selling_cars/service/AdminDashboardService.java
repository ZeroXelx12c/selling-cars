package com.example.selling_cars.service;

import com.example.selling_cars.entity.AdminDashboard;
import com.example.selling_cars.repository.AdminDashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AdminDashboardService {
    @Autowired
    private AdminDashboardRepository adminDashboardRepository;

    // Lấy tất cả báo cáo
    public List<AdminDashboard> getAllReports() {
        return adminDashboardRepository.findAll();
    }

    // Lấy báo cáo theo ID
    public AdminDashboard getReportById(Integer id) {
        return adminDashboardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));
    }

    // Lấy báo cáo theo ngày
    public Optional<AdminDashboard> getReportByDate(LocalDate reportDate) {
        return adminDashboardRepository.findByReportDate(reportDate);
    }

    // Lấy báo cáo theo khoảng thời gian
    public List<AdminDashboard> getReportsByDateRange(LocalDate startDate, LocalDate endDate) {
        return adminDashboardRepository.findByReportDateBetween(startDate, endDate);
    }

    // Lấy báo cáo mới nhất
    public Optional<AdminDashboard> getLatestReport() {
        return adminDashboardRepository.findFirstByOrderByReportDateDesc();
    }

    // Lấy báo cáo theo tổng doanh thu
    public List<AdminDashboard> getReportsByRevenue(BigDecimal minRevenue) {
        return adminDashboardRepository.findByTotalRevenueGreaterThanEqual(minRevenue);
    }

    // Lấy báo cáo theo số đơn hàng mới
    public List<AdminDashboard> getReportsByNewOrders(Integer minOrders) {
        return adminDashboardRepository.findByNewOrdersGreaterThanEqual(minOrders);
    }

    // Lấy báo cáo theo số lượng tồn kho
    public List<AdminDashboard> getReportsByInventory(Integer maxInventory) {
        return adminDashboardRepository.findByInventoryCountLessThanEqual(maxInventory);
    }

    // Lấy báo cáo theo tổng số khách hàng
    public List<AdminDashboard> getReportsByCustomers(Integer minCustomers) {
        return adminDashboardRepository.findByTotalCustomersGreaterThanEqual(minCustomers);
    }

    // Lấy danh sách báo cáo sắp xếp theo ngày
    public List<AdminDashboard> getAllReportsOrderByDate() {
        return adminDashboardRepository.findAllByOrderByReportDateDesc();
    }

    // Thêm báo cáo mới
    @Transactional
    public AdminDashboard createReport(AdminDashboard report) {
        if (adminDashboardRepository.findByReportDate(report.getReportDate()).isPresent()) {
            throw new RuntimeException("Report for date " + report.getReportDate() + " already exists");
        }
        return adminDashboardRepository.save(report);
    }

    // Cập nhật báo cáo
    @Transactional
    public AdminDashboard updateReport(Integer id, AdminDashboard reportDetails) {
        AdminDashboard report = adminDashboardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));

        // Kiểm tra ngày mới có trùng với báo cáo khác không
        if (!report.getReportDate().equals(reportDetails.getReportDate()) &&
            adminDashboardRepository.findByReportDate(reportDetails.getReportDate()).isPresent()) {
            throw new RuntimeException("Report for date " + reportDetails.getReportDate() + " already exists");
        }

        report.setReportDate(reportDetails.getReportDate());
        report.setTotalRevenue(reportDetails.getTotalRevenue());
        report.setNewOrders(reportDetails.getNewOrders());
        report.setInventoryCount(reportDetails.getInventoryCount());
        report.setTotalCustomers(reportDetails.getTotalCustomers());

        return adminDashboardRepository.save(report);
    }

    // Xóa báo cáo
    @Transactional
    public void deleteReport(Integer id) {
        AdminDashboard report = adminDashboardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));
        adminDashboardRepository.delete(report);
    }

    // Đếm số lượng báo cáo theo khoảng thời gian
    public long countReportsByDateRange(LocalDate startDate, LocalDate endDate) {
        return adminDashboardRepository.countByReportDateBetween(startDate, endDate);
    }

    // Tạo báo cáo hàng ngày tự động
    public AdminDashboard generateDailyReport() {
        // Kiểm tra xem đã có báo cáo của ngày hôm nay chưa
        LocalDate today = LocalDate.now();
        Optional<AdminDashboard> existingReport = getReportByDate(today);
        if (existingReport.isPresent()) {
            return existingReport.get();
        }

        // Tạo báo cáo mới
        AdminDashboard newReport = new AdminDashboard();
        newReport.setReportDate(today);

        // Lấy dữ liệu từ các service khác
        // TODO: Inject và sử dụng các service liên quan để lấy dữ liệu thực tế
        // Ví dụ: dùng OrderService để lấy tổng doanh thu, số đơn hàng mới
        // Dùng ProductService để lấy số lượng tồn kho
        // Dùng UserService để lấy tổng số khách hàng

        // Tạm thời đặt giá trị mẫu
        newReport.setTotalRevenue(BigDecimal.ZERO);
        newReport.setNewOrders(0);
        newReport.setInventoryCount(0);
        newReport.setTotalCustomers(0);

        return createReport(newReport);
    }
} 