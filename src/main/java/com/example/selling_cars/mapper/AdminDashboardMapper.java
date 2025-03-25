package com.example.selling_cars.mapper;

import com.example.selling_cars.dto.AdminDashboardDTO;
import com.example.selling_cars.entity.AdminDashboard;
import org.springframework.stereotype.Component;

@Component
public class AdminDashboardMapper {
    
    public AdminDashboardDTO toDTO(AdminDashboard dashboard) {
        if (dashboard == null) {
            return null;
        }
        
        AdminDashboardDTO dto = new AdminDashboardDTO();
        dto.setReportId(dashboard.getDashboardId());
        dto.setReportDate(dashboard.getReportDate());
        dto.setTotalRevenue(dashboard.getTotalRevenue());
        dto.setNewOrders(dashboard.getNewOrders());
        dto.setInventoryCount(dashboard.getInventoryCount());
        dto.setTotalCustomers(dashboard.getTotalCustomers());
        
        return dto;
    }

    public AdminDashboard toEntity(AdminDashboardDTO dto) {
        if (dto == null) {
            return null;
        }
        
        AdminDashboard dashboard = new AdminDashboard();
        dashboard.setDashboardId(dto.getReportId());
        dashboard.setReportDate(dto.getReportDate());
        dashboard.setTotalRevenue(dto.getTotalRevenue());
        dashboard.setNewOrders(dto.getNewOrders());
        dashboard.setInventoryCount(dto.getInventoryCount());
        dashboard.setTotalCustomers(dto.getTotalCustomers());
        
        return dashboard;
    }
} 