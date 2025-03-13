package com.example.selling_cars.controller;

import com.example.selling_cars.dto.PaymentDTO;
import com.example.selling_cars.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        return ResponseEntity.ok(paymentsService.getAllPayments());
    }

    // Lấy thanh toán theo ID
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Integer id) {
        return paymentsService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lấy thanh toán theo đơn hàng (cho người dùng/admin)
    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentDTO> getPaymentByOrder(@PathVariable Integer orderId) {
        return ResponseEntity.ok(paymentsService.getPaymentByOrder(orderId));
    }

    // Lấy danh sách thanh toán theo người dùng (cho người dùng)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(paymentsService.getPaymentsByUser(userId));
    }

    // Tính tổng doanh thu trong khoảng thời gian (cho admin)
    @GetMapping("/revenue")
    public ResponseEntity<Double> getTotalRevenueBetweenDates(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return ResponseEntity.ok(paymentsService.getTotalRevenueBetweenDates(start, end));
    }

    // Đếm số giao dịch trong ngày (cho admin)
    @GetMapping("/count-today")
    public ResponseEntity<Long> getPaymentsCountToday() {
        return ResponseEntity.ok(paymentsService.getPaymentsCountToday());
    }

    // Ghi thanh toán mới (cho người dùng/admin)
    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) {
        return ResponseEntity.ok(paymentsService.createPayment(paymentDTO));
    }

    // Cập nhật thanh toán (cho admin)
    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Integer id, @RequestBody PaymentDTO paymentDTO) {
        return ResponseEntity.ok(paymentsService.updatePayment(id, paymentDTO));
    }

    // Xóa thanh toán (cho admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Integer id) {
        paymentsService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}