package com.example.selling_cars.mapper;

import com.example.selling_cars.dto.PaymentDTO;
import com.example.selling_cars.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    
    public PaymentDTO toDTO(Payment payment) {
        if (payment == null) {
            return null;
        }
        
        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setTransactionId(payment.getTransactionId());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setAmount(payment.getAmount());
        dto.setPaymentStatus(payment.getPaymentStatus());
        
        if (payment.getOrder() != null) {
            dto.setOrderId(payment.getOrder().getOrderId());
        }
        
        return dto;
    }

    public Payment toEntity(PaymentDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Payment payment = new Payment();
        payment.setPaymentId(dto.getPaymentId());
        payment.setTransactionId(dto.getTransactionId());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setPaymentDate(dto.getPaymentDate());
        payment.setAmount(dto.getAmount());
        payment.setPaymentStatus(dto.getPaymentStatus());
        
        return payment;
    }
} 