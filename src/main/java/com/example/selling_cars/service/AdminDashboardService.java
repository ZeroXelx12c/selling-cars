package com.example.selling_cars.service;

import com.example.selling_cars.dto.AdminDashboardDTO;
import com.example.selling_cars.entity.AdminDashboard;
import com.example.selling_cars.repository.AdminDashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminDashboardService {

    @Autowired
    private AdminDashboardRepository adminDashboardRepository;

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ProductsService productsService;

    // Lấy tất cả bản ghi tổng quan
    public List<AdminDashboardDTO> getAllDashboards() {
        return adminDashboardRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Lấy bản ghi tổng quan mới nhất
    public Optional<AdminDashboardDTO> getLatestDashboard() {
        return adminDashboardRepository.findTopByOrderByReportDateDesc().map(this::convertToDTO);
    }

    // Lấy bản ghi tổng quan theo ngày
    public Optional<AdminDashboardDTO> getDashboardByDate(LocalDate reportDate) {
        return adminDashboardRepository.findByReportDate(reportDate).map(this::convertToDTO);
    }

    // Cập nhật dữ liệu tổng quan (gọi stored procedure hoặc tính toán thủ công)
    public AdminDashboardDTO updateDashboard() {
        // Gọi stored procedure để cập nhật
        adminDashboardRepository.updateAdminDashboard();

        // Lấy bản ghi mới nhất sau khi cập nhật
        AdminDashboard latestDashboard = adminDashboardRepository.findTopByOrderByReportDateDesc()
                .orElseThrow(() -> new RuntimeException("Không thể cập nhật tổng quan!"));

        return convertToDTO(latestDashboard);
    }

    // Tính toán thủ công dữ liệu tổng quan (nếu không dùng stored procedure)
    public AdminDashboardDTO calculateDashboardManually() {
        AdminDashboard dashboard = new AdminDashboard();
        dashboard.setTotalRevenue(paymentsService.getTotalRevenueBetweenDates(
                LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.now()));
        dashboard.setNewOrders((int) ordersService.getNewOrdersToday());
        dashboard.setTotalCustomers((int) usersService.getTotalCustomers());
        dashboard.setInventoryCount((int) productsService.getInStockCount());
        dashboard.setReportDate(LocalDate.now());

        AdminDashboard savedDashboard = adminDashboardRepository.save(dashboard);
        return convertToDTO(savedDashboard);
    }

    // Xóa bản ghi tổng quan (hiếm dùng, chỉ cho mục đích quản lý)
    public void deleteDashboard(Integer id) {
        AdminDashboard dashboard = adminDashboardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản ghi tổng quan!"));
        adminDashboardRepository.delete(dashboard);
    }

    // Chuyển từ Entity sang DTO
    private AdminDashboardDTO convertToDTO(AdminDashboard dashboard) {
        AdminDashboardDTO dto = new AdminDashboardDTO();
        dto.setDashboardId(dashboard.getDashboardId());
        dto.setTotalRevenue(dashboard.getTotalRevenue());
        dto.setNewOrders(dashboard.getNewOrders());
        dto.setTotalCustomers(dashboard.getTotalCustomers());
        dto.setInventoryCount(dashboard.getInventoryCount());
        dto.setReportDate(dashboard.getReportDate());
        return dto;
    }
}