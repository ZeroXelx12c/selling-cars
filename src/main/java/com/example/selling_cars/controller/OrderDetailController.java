package com.example.selling_cars.controller;

import com.example.selling_cars.dto.OrderDetailDTO;
import com.example.selling_cars.entity.OrderDetail;
import com.example.selling_cars.exception.ResourceNotFoundException;
import com.example.selling_cars.mapper.OrderDetailMapper;
import com.example.selling_cars.service.OrderDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order-details")
@CrossOrigin(origins = "*")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
        List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetails();
        List<OrderDetailDTO> orderDetailDTOs = orderDetails.stream()
                .map(orderDetailMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDetailDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> getOrderDetailById(@PathVariable Integer id) {
        Optional<OrderDetail> orderDetailOpt = orderDetailService.getOrderDetailById(id);
        OrderDetail orderDetail = orderDetailOpt.orElseThrow(() ->
                new ResourceNotFoundException("OrderDetail", "id", id));
        return ResponseEntity.ok(orderDetailMapper.toDTO(orderDetail));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderDetailDTO>> getOrderDetailsByOrder(@PathVariable Integer orderId) {
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrder(orderId);
        List<OrderDetailDTO> orderDetailDTOs = orderDetails.stream()
                .map(orderDetailMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDetailDTOs);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<OrderDetailDTO>> getOrderDetailsByProduct(@PathVariable Integer productId) {
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByProduct(productId);
        List<OrderDetailDTO> orderDetailDTOs = orderDetails.stream()
                .map(orderDetailMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDetailDTOs);
    }

    @GetMapping("/exterior-option/{optionId}")
    public ResponseEntity<List<OrderDetailDTO>> getOrderDetailsByExteriorOption(@PathVariable Integer optionId) {
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByExteriorOption(optionId);
        List<OrderDetailDTO> orderDetailDTOs = orderDetails.stream()
                .map(orderDetailMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDetailDTOs);
    }

    @GetMapping("/interior-option/{optionId}")
    public ResponseEntity<List<OrderDetailDTO>> getOrderDetailsByInteriorOption(@PathVariable Integer optionId) {
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByInteriorOption(optionId);
        List<OrderDetailDTO> orderDetailDTOs = orderDetails.stream()
                .map(orderDetailMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDetailDTOs);
    }

    @PostMapping
    public ResponseEntity<OrderDetailDTO> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = orderDetailMapper.toEntity(orderDetailDTO);
        OrderDetail savedOrderDetail = orderDetailService.createOrderDetail(orderDetail);
        return new ResponseEntity<>(orderDetailMapper.toDTO(savedOrderDetail), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> updateOrderDetail(
            @PathVariable Integer id,
            @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = orderDetailMapper.toEntity(orderDetailDTO);
        OrderDetail updatedOrderDetail = orderDetailService.updateOrderDetail(id, orderDetail);
        return ResponseEntity.ok(orderDetailMapper.toDTO(updatedOrderDetail));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Integer id) {
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count/order/{orderId}")
    public ResponseEntity<Long> countOrderDetailsByOrder(@PathVariable Integer orderId) {
        long count = orderDetailService.countByOrder(orderId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/product/{productId}")
    public ResponseEntity<Long> countOrderDetailsByProduct(@PathVariable Integer productId) {
        long count = orderDetailService.countByProduct(productId);
        return ResponseEntity.ok(count);
    }
} 