package com.example.selling_cars.service;

import com.example.selling_cars.entity.RevenueReport;
import com.example.selling_cars.repository.RevenueReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RevenueReportService {
    @Autowired
    private RevenueReportRepository revenueReportRepository;

    // Lấy tất cả báo cáo doanh thu
    public List<RevenueReport> getAllReports() {
        return revenueReportRepository.findAll();
    }

    // Lấy báo cáo theo ID
    public RevenueReport getReportById(Integer id) {
        return revenueReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));
    }

    // Lấy báo cáo theo tháng và năm
    public Optional<RevenueReport> getReportByYearMonth(YearMonth yearMonth) {
        Integer month = yearMonth.getMonthValue();
        Integer year = yearMonth.getYear();
        return revenueReportRepository.findByReportMonthAndReportYear(month, year);
    }

    // Lấy báo cáo theo năm
    public List<RevenueReport> getReportsByYear(Integer year) {
        return revenueReportRepository.findByReportYear(year);
    }

    // Lấy báo cáo theo khoảng thời gian
    public List<RevenueReport> getReportsByDateRange(LocalDate startDate, LocalDate endDate) {
        // Chuyển đổi LocalDate sang LocalDateTime để phù hợp với kiểu dữ liệu trong báo cáo
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.plusDays(1).atStartOfDay().minusNanos(1);
        return revenueReportRepository.findByGeneratedDateBetween(start, end);
    }

    // Lấy báo cáo mới nhất
    public Optional<RevenueReport> getLatestReport() {
        return revenueReportRepository.findTopByOrderByGeneratedDateDesc();
    }

    // Lấy báo cáo theo tổng doanh thu
    public List<RevenueReport> getReportsByMinRevenue(BigDecimal minRevenue) {
        return revenueReportRepository.findByTotalRevenueGreaterThanEqual(minRevenue);
    }

    // Lấy báo cáo theo số lượng đơn hàng
    public List<RevenueReport> getReportsByMinOrders(Integer minOrders) {
        return revenueReportRepository.findByOrderCountGreaterThanEqual(minOrders);
    }

    // Lấy báo cáo theo giá trị đơn hàng trung bình
    public List<RevenueReport> getReportsByMinAverageOrderValue(BigDecimal minAverage) {
        return revenueReportRepository.findByAverageOrderValueGreaterThanEqual(minAverage);
    }

    // Thêm báo cáo mới
    @Transactional
    public RevenueReport createReport(RevenueReport report) {
        // Đặt ngày tạo báo cáo nếu chưa có
        if (report.getGeneratedDate() == null) {
            report.setGeneratedDate(LocalDateTime.now());
        }
        
        // Kiểm tra xem đã có báo cáo của tháng và năm này chưa
        if (revenueReportRepository.findByReportMonthAndReportYear(
                report.getReportMonth(), report.getReportYear()).isPresent()) {
            throw new RuntimeException("Report for month " + report.getReportMonth() + 
                    " and year " + report.getReportYear() + " already exists");
        }
        
        return revenueReportRepository.save(report);
    }

    // Cập nhật báo cáo
    @Transactional
    public RevenueReport updateReport(Integer id, RevenueReport reportDetails) {
        RevenueReport report = revenueReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));

        // Kiểm tra nếu tháng và năm thay đổi, có báo cáo nào khác với cùng tháng và năm không
        if ((report.getReportMonth() != reportDetails.getReportMonth() || 
                report.getReportYear() != reportDetails.getReportYear()) &&
                revenueReportRepository.findByReportMonthAndReportYear(
                        reportDetails.getReportMonth(), reportDetails.getReportYear()).isPresent()) {
            throw new RuntimeException("Report for month " + reportDetails.getReportMonth() + 
                    " and year " + reportDetails.getReportYear() + " already exists");
        }

        report.setReportMonth(reportDetails.getReportMonth());
        report.setReportYear(reportDetails.getReportYear());
        report.setGeneratedDate(reportDetails.getGeneratedDate());
        report.setTotalRevenue(reportDetails.getTotalRevenue());
        report.setOrderCount(reportDetails.getOrderCount());
        report.setAverageOrderValue(reportDetails.getAverageOrderValue());
        report.setTopSellingProducts(reportDetails.getTopSellingProducts());
        report.setRevenueByCategory(reportDetails.getRevenueByCategory());

        return revenueReportRepository.save(report);
    }

    // Xóa báo cáo
    @Transactional
    public void deleteReport(Integer id) {
        RevenueReport report = revenueReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));
        revenueReportRepository.delete(report);
    }

    // Tạo báo cáo tổng hợp theo năm
    public RevenueReport generateYearlySummary(Integer year) {
        List<RevenueReport> yearReports = revenueReportRepository.findByReportYear(year);
        
        if (yearReports.isEmpty()) {
            throw new RuntimeException("No reports found for year: " + year);
        }
        
        RevenueReport summary = new RevenueReport();
        summary.setReportYear(year);
        summary.setReportMonth(0); // 0 là giá trị đại diện cho báo cáo năm
        summary.setGeneratedDate(LocalDateTime.now());
        
        BigDecimal totalRevenue = BigDecimal.ZERO;
        int totalOrders = 0;
        
        for (RevenueReport report : yearReports) {
            totalRevenue = totalRevenue.add(report.getTotalRevenue());
            totalOrders += report.getOrderCount();
        }
        
        summary.setTotalRevenue(totalRevenue);
        summary.setOrderCount(totalOrders);
        
        if (totalOrders > 0) {
            summary.setAverageOrderValue(totalRevenue.divide(
                    BigDecimal.valueOf(totalOrders), 2, java.math.RoundingMode.HALF_UP));
        } else {
            summary.setAverageOrderValue(BigDecimal.ZERO);
        }
        
        // Đặt giá trị mẫu cho các trường khác
        summary.setTopSellingProducts("Yearly summary");
        summary.setRevenueByCategory("Yearly summary by category");
        
        return summary;
    }

    // Tạo báo cáo hàng tháng
    public RevenueReport generateMonthlyReport(YearMonth yearMonth) {
        // Kiểm tra xem đã có báo cáo cho tháng này chưa
        Optional<RevenueReport> existingReport = getReportByYearMonth(yearMonth);
        if (existingReport.isPresent()) {
            return existingReport.get();
        }
        
        // Tạo báo cáo mới
        RevenueReport report = new RevenueReport();
        report.setReportMonth(yearMonth.getMonthValue());
        report.setReportYear(yearMonth.getYear());
        report.setGeneratedDate(LocalDateTime.now());
        
        // TODO: lấy dữ liệu từ các service khác để tính toán
        // Ví dụ: dùng OrderService để lấy doanh thu và số đơn hàng trong tháng
        
        // Tạm thời đặt giá trị mẫu
        report.setTotalRevenue(BigDecimal.ZERO);
        report.setOrderCount(0);
        report.setAverageOrderValue(BigDecimal.ZERO);
        report.setTopSellingProducts("No data");
        report.setRevenueByCategory("No data");
        
        return createReport(report);
    }

    // Lấy danh sách sản phẩm bán chạy trong tháng
    public List<String> getTopSellingProducts(YearMonth yearMonth, Integer limit) {
        // TODO: Thực hiện truy vấn từ OrderDetail để lấy sản phẩm bán chạy
        
        // Tạm thời trả về danh sách mẫu
        return Arrays.asList(
            "Product 1 - 10 sold",
            "Product 2 - 8 sold",
            "Product 3 - 7 sold",
            "Product 4 - 5 sold",
            "Product 5 - 3 sold"
        ).stream().limit(limit).collect(Collectors.toList());
    }

    // Lấy doanh thu theo danh mục trong tháng
    public List<Object[]> getRevenueByCategoryForMonth(YearMonth yearMonth) {
        // TODO: Thực hiện truy vấn từ OrderDetail và Category để lấy doanh thu theo danh mục
        
        // Tạm thời trả về danh sách mẫu
        List<Object[]> result = new ArrayList<>();
        result.add(new Object[]{"Sedan", BigDecimal.valueOf(50000)});
        result.add(new Object[]{"SUV", BigDecimal.valueOf(75000)});
        result.add(new Object[]{"Truck", BigDecimal.valueOf(30000)});
        result.add(new Object[]{"Luxury", BigDecimal.valueOf(100000)});
        
        return result;
    }
} 