     package com.example.selling_cars.service;

import com.example.selling_cars.entity.Payments;
import com.example.selling_cars.repository.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentsService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    // Lấy tất cả thanh toán
    public List<Payments> getAllPayments() {
        return paymentsRepository.findAll();
    }

    // Lấy thanh toán theo ID
    public Optional<Payments> getPaymentById(Integer id) {
        return paymentsRepository.findById(id);
    }

    // Lấy thanh toán theo OrderID
    public Payments getPaymentByOrder(Integer orderId) {
        Payments payment = paymentsRepository.findByOrderOrderId(orderId);
        if (payment == null) {
            throw new RuntimeException("Không tìm thấy thanh toán cho đơn hàng này!");
        }
        return payment;
    }

    // Lấy thanh toán theo phương thức thanh toán
    public List<Payments> getPaymentsByMethod(String paymentMethod) {
        return paymentsRepository.findByPaymentMethod(paymentMethod);
    }

    // Tính tổng doanh thu theo tháng
    public Double getMonthlyRevenue(int month, int year) {
        Double revenue = paymentsRepository.getMonthlyRevenue(month, year);
        return revenue != null ? revenue : 0.0;
    }

    // Lấy danh sách thanh toán trong khoảng thời gian
    public List<Payments> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return paymentsRepository.findByPaymentDateBetween(startDate, endDate);
    }

    // Thêm thanh toán mới
    public Payments createPayment(Payments payment) {
        // Kiểm tra xem đơn hàng đã có thanh toán chưa
        if (paymentsRepository.findByOrderOrderId(payment.getOrder().getOrderId()) != null) {
            throw new RuntimeException("Đơn hàng này đã có thanh toán!");
        }
        return paymentsRepository.save(payment);
    }

    // Cập nhật thanh toán
    public Payments updatePayment(Integer id, Payments paymentDetails) {
        Payments payment = paymentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thanh toán!"));

        payment.setOrder(paymentDetails.getOrder());
        payment.setAmount(paymentDetails.getAmount());
        payment.setPaymentDate(paymentDetails.getPaymentDate());
        payment.setPaymentMethod(paymentDetails.getPaymentMethod());
        payment.setTransactionId(paymentDetails.getTransactionId());

        return paymentsRepository.save(payment);
    }

    // Xóa thanh toán
    public void deletePayment(Integer id) {
        Payments payment = paymentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thanh toán!"));
        paymentsRepository.delete(payment);
    }
}