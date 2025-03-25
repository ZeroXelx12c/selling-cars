package com.example.selling_cars.service;

import com.example.selling_cars.entity.Payment;
import com.example.selling_cars.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    // Lấy tất cả thanh toán
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Lấy thanh toán theo ID
    public Optional<Payment> getPaymentById(Integer id) {
        return paymentRepository.findById(id);
    }

    // Lấy thanh toán theo đơn hàng
    public List<Payment> getPaymentsByOrder(Integer orderId) {
        return paymentRepository.findByOrderOrderId(orderId);
    }

    // Lấy thanh toán theo phương thức thanh toán
    public List<Payment> getPaymentsByMethod(String paymentMethod) {
        return paymentRepository.findByPaymentMethod(paymentMethod);
    }

    // Lấy thanh toán theo khoảng thời gian
    public List<Payment> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return paymentRepository.findByPaymentDateBetween(startDate, endDate);
    }

    // Lấy thanh toán theo ID giao dịch
    public Optional<Payment> getPaymentByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId);
    }

    // Lấy thanh toán theo đơn hàng và phương thức thanh toán
    public List<Payment> getPaymentsByOrderAndMethod(Integer orderId, String paymentMethod) {
        return paymentRepository.findByOrderOrderIdAndPaymentMethod(orderId, paymentMethod);
    }

    // Lấy thanh toán theo đơn hàng và khoảng thời gian
    public List<Payment> getPaymentsByOrderAndDateRange(Integer orderId, LocalDateTime startDate, LocalDateTime endDate) {
        return paymentRepository.findByOrderOrderIdAndPaymentDateBetween(orderId, startDate, endDate);
    }

    // Thêm thanh toán mới
    @Transactional
    public Payment createPayment(Payment payment) {
        if (paymentRepository.existsByTransactionId(payment.getTransactionId())) {
            throw new RuntimeException("Payment with transaction ID " + payment.getTransactionId() + " already exists");
        }
        payment.setPaymentDate(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    // Cập nhật thanh toán
    @Transactional
    public Payment updatePayment(Integer id, Payment paymentDetails) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));

        // Kiểm tra ID giao dịch mới có trùng với thanh toán khác không
        if (!payment.getTransactionId().equals(paymentDetails.getTransactionId()) &&
            paymentRepository.existsByTransactionId(paymentDetails.getTransactionId())) {
            throw new RuntimeException("Payment with transaction ID " + paymentDetails.getTransactionId() + " already exists");
        }

        payment.setTransactionId(paymentDetails.getTransactionId());
        payment.setPaymentMethod(paymentDetails.getPaymentMethod());
        payment.setAmount(paymentDetails.getAmount());
        payment.setPaymentStatus(paymentDetails.getPaymentStatus());
        payment.setOrder(paymentDetails.getOrder());

        return paymentRepository.save(payment);
    }

    // Xóa thanh toán
    @Transactional
    public void deletePayment(Integer id) {
        paymentRepository.deleteById(id);
    }

    // Cập nhật trạng thái thanh toán
    @Transactional
    public Payment updatePaymentStatus(Integer id, String status) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        payment.setPaymentStatus(status);
        return paymentRepository.save(payment);
    }

    // Đếm số lượng thanh toán theo phương thức
    public long countByMethod(String paymentMethod) {
        return paymentRepository.countByPaymentMethod(paymentMethod);
    }

    // Đếm số lượng thanh toán theo đơn hàng
    public long countByOrder(Integer orderId) {
        return paymentRepository.countByOrderOrderId(orderId);
    }

    // Kiểm tra thanh toán có tồn tại
    public boolean existsByTransactionId(String transactionId) {
        return paymentRepository.existsByTransactionId(transactionId);
    }
} 