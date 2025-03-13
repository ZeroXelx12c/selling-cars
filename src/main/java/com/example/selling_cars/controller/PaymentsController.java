package com.example.selling_cars.controller;

import com.example.selling_cars.entity.Payments;
import com.example.selling_cars.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    @Autowired
    private PaymentsService paymentsService;

    // Lấy tất cả thanh toán (cho admin)
    @GetMapping
    public ResponseEntity<List<Payments>> getAllPayments() {
        return ResponseEntity.ok(paymentsService.getAllPayments());
    }

    // Lấy thanh toán theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Payments> getPaymentById(@PathVariable Integer id) {
        return paymentsService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lấy thanh toán theo OrderID (cho người dùng/admin)
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Payments> getPaymentByOrder(@PathVariable Integer orderId) {
        return ResponseEntity.ok(paymentsService.getPaymentByOrder(orderId));
    }

    // Lấy thanh toán theo phương thức thanh toán (cho admin)
    @GetMapping("/method/{paymentMethod}")
    public ResponseEntity<List<Payments>> getPaymentsByMethod(@PathVariable String paymentMethod) {
        return ResponseEntity.ok(paymentsService.getPaymentsByMethod(paymentMethod));
    }

    // Tính tổng doanh thu theo tháng (cho admin dashboard)
    @GetMapping("/monthly-revenue")
    public ResponseEntity<Double> getMonthlyRevenue(
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(paymentsService.getMonthlyRevenue(month, year));
    }

    // Lấy danh sách thanh toán trong khoảng thời gian (cho admin)
    @GetMapping("/date-range")
    public ResponseEntity<List<Payments>> getPaymentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(paymentsService.getPaymentsByDateRange(startDate, endDate));
    }

    // Thêm thanh toán mới (cho admin hoặc tích hợp khi thanh toán đơn hàng)
    @PostMapping
    public ResponseEntity<Payments> createPayment(@RequestBody Payments payment) {
        return ResponseEntity.ok(paymentsService.createPayment(payment));
    }

    // Cập nhật thanh toán (cho admin)
    @PutMapping("/{id}")
    public ResponseEntity<Payments> updatePayment(@PathVariable Integer id, @RequestBody Payments paymentDetails) {
        return ResponseEntity.ok(paymentsService.updatePayment(id, paymentDetails));
    }

    // Xóa thanh toán (cho admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Integer id) {
        paymentsService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}