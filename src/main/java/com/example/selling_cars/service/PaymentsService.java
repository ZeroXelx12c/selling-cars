package com.example.selling_cars.service;

import com.example.selling_cars.dto.PaymentDTO;
import com.example.selling_cars.entity.Orders;
import com.example.selling_cars.entity.Payments;
import com.example.selling_cars.repository.OrdersRepository;
import com.example.selling_cars.repository.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentsService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    // Lấy tất cả thanh toán
    public List<PaymentDTO> getAllPayments() {
        return paymentsRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Lấy thanh toán theo ID
    public Optional<PaymentDTO> getPaymentById(Integer id) {
        return paymentsRepository.findById(id).map(this::convertToDTO);
    }

    // Lấy thanh toán theo đơn hàng
    public PaymentDTO getPaymentByOrder(Integer orderId) {
        Payments payment = paymentsRepository.findByOrderOrderId(orderId);
        if (payment == null) {
            throw new RuntimeException("Không tìm thấy thanh toán cho đơn hàng này!");
        }
        return convertToDTO(payment);
    }

    // Lấy danh sách thanh toán theo người dùng
    public List<PaymentDTO> getPaymentsByUser(Integer userId) {
        return paymentsRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Tính tổng doanh thu trong khoảng thời gian
    public Double getTotalRevenueBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        Double total = paymentsRepository.getTotalRevenueBetweenDates(startDate, endDate);
        return total != null ? total : 0.0;
    }

    // Đếm số giao dịch trong ngày
    public long getPaymentsCountToday() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        return paymentsRepository.countPaymentsToday(startOfDay);
    }

    // Ghi thanh toán mới
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Orders order = ordersRepository.findById(paymentDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng!"));

        if (paymentsRepository.findByOrderOrderId(paymentDTO.getOrderId()) != null) {
            throw new RuntimeException("Đơn hàng này đã được thanh toán!");
        }

        Payments payment = new Payments();
        payment.setOrder(order);
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setTransactionId(paymentDTO.getTransactionId());

        Payments savedPayment = paymentsRepository.save(payment);
        return convertToDTO(savedPayment);
    }

    // Cập nhật thanh toán
    public PaymentDTO updatePayment(Integer id, PaymentDTO paymentDTO) {
        Payments payment = paymentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thanh toán!"));

        Orders order = ordersRepository.findById(paymentDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng!"));

        payment.setOrder(order);
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setTransactionId(paymentDTO.getTransactionId());

        Payments updatedPayment = paymentsRepository.save(payment);
        return convertToDTO(updatedPayment);
    }

    // Xóa thanh toán
    public void deletePayment(Integer id) {
        Payments payment = paymentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thanh toán!"));
        paymentsRepository.delete(payment);
    }

    // Chuyển từ Entity sang DTO
    private PaymentDTO convertToDTO(Payments payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setOrderId(payment.getOrder().getOrderId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setTransactionId(payment.getTransactionId());
        return dto;
    }
}