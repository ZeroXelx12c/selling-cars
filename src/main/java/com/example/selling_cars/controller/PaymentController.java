package com.example.selling_cars.controller;

import com.example.selling_cars.dto.PaymentDTO;
import com.example.selling_cars.entity.Payment;
import com.example.selling_cars.exception.ResourceNotFoundException;
import com.example.selling_cars.mapper.PaymentMapper;
import com.example.selling_cars.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentMapper paymentMapper;

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        List<PaymentDTO> paymentDTOs = payments.stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(paymentDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Integer id) {
        Optional<Payment> paymentOpt = paymentService.getPaymentById(id);
        Payment payment = paymentOpt.orElseThrow(() ->
                new ResourceNotFoundException("Payment", "id", id));
        return ResponseEntity.ok(paymentMapper.toDTO(payment));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByOrder(@PathVariable Integer orderId) {
        List<Payment> payments = paymentService.getPaymentsByOrder(orderId);
        List<PaymentDTO> paymentDTOs = payments.stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(paymentDTOs);
    }

    @GetMapping("/method/{paymentMethod}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByMethod(@PathVariable String paymentMethod) {
        List<Payment> payments = paymentService.getPaymentsByMethod(paymentMethod);
        List<PaymentDTO> paymentDTOs = payments.stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(paymentDTOs);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Payment> payments = paymentService.getPaymentsByDateRange(startDate, endDate);
        List<PaymentDTO> paymentDTOs = payments.stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(paymentDTOs);
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<PaymentDTO> getPaymentByTransactionId(@PathVariable String transactionId) {
        Optional<Payment> paymentOpt = paymentService.getPaymentByTransactionId(transactionId);
        Payment payment = paymentOpt.orElseThrow(() ->
                new ResourceNotFoundException("Payment", "transactionId", transactionId));
        return ResponseEntity.ok(paymentMapper.toDTO(payment));
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        Payment payment = paymentMapper.toEntity(paymentDTO);
        Payment savedPayment = paymentService.createPayment(payment);
        return new ResponseEntity<>(paymentMapper.toDTO(savedPayment), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(
            @PathVariable Integer id,
            @Valid @RequestBody PaymentDTO paymentDTO) {
        Payment payment = paymentMapper.toEntity(paymentDTO);
        Payment updatedPayment = paymentService.updatePayment(id, payment);
        return ResponseEntity.ok(paymentMapper.toDTO(updatedPayment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Integer id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count/method/{paymentMethod}")
    public ResponseEntity<Long> countPaymentsByMethod(@PathVariable String paymentMethod) {
        long count = paymentService.countByMethod(paymentMethod);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/order/{orderId}")
    public ResponseEntity<Long> countPaymentsByOrder(@PathVariable Integer orderId) {
        long count = paymentService.countByOrder(orderId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/exists/transaction/{transactionId}")
    public ResponseEntity<Boolean> existsByTransactionId(@PathVariable String transactionId) {
        boolean exists = paymentService.existsByTransactionId(transactionId);
        return ResponseEntity.ok(exists);
    }
}