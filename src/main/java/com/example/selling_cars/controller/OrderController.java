package com.example.selling_cars.controller;

import com.example.selling_cars.dto.OrderDTO;
import com.example.selling_cars.entity.Order;
import com.example.selling_cars.exception.ResourceNotFoundException;
import com.example.selling_cars.mapper.OrderMapper;
import com.example.selling_cars.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderDTO> orderDTOs = orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Integer id) {
        Optional<Order> orderOpt = orderService.getOrderById(id);
        Order order = orderOpt.orElseThrow(() ->
                new ResourceNotFoundException("Order", "id", id));
        return ResponseEntity.ok(orderMapper.toDTO(order));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(@PathVariable Integer userId) {
        List<Order> orders = orderService.getOrdersByUser(userId);
        List<OrderDTO> orderDTOs = orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@PathVariable String status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        List<OrderDTO> orderDTOs = orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserAndStatus(
            @PathVariable Integer userId,
            @PathVariable String status) {
        List<Order> orders = orderService.getOrdersByUserAndStatus(userId, status);
        List<OrderDTO> orderDTOs = orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/dateRange")
    public ResponseEntity<List<OrderDTO>> getOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Order> orders = orderService.getOrdersByDateRange(startDate, endDate);
        List<OrderDTO> orderDTOs = orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/priceRange")
    public ResponseEntity<List<OrderDTO>> getOrdersByPriceRange(
            @RequestParam BigDecimal minAmount,
            @RequestParam BigDecimal maxAmount) {
        List<Order> orders = orderService.getOrdersByPriceRange(minAmount, maxAmount);
        List<OrderDTO> orderDTOs = orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/deliveryArea/{area}")
    public ResponseEntity<List<OrderDTO>> getOrdersByDeliveryArea(@PathVariable String area) {
        List<Order> orders = orderService.getOrdersByDeliveryArea(area);
        List<OrderDTO> orderDTOs = orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<OrderDTO>> filterOrders(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam(required = false) String deliveryArea,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        Page<Order> orderPage = orderService.findByFilters(userId, status, startDate, endDate, minAmount, maxAmount, deliveryArea, pageable);
        
        Page<OrderDTO> orderDTOPage = orderPage.map(orderMapper::toDTO);
        return ResponseEntity.ok(orderDTOPage);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        Order savedOrder = orderService.createOrder(order);
        return new ResponseEntity<>(orderMapper.toDTO(savedOrder), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable Integer id,
            @Valid @RequestBody OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        Order updatedOrder = orderService.updateOrder(id, order);
        return ResponseEntity.ok(orderMapper.toDTO(updatedOrder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Integer id,
            @RequestParam String status) {
        Order order = orderService.getOrderById(id).orElseThrow(() ->
                new ResourceNotFoundException("Order", "id", id));
        order.setOrderStatus(status);
        Order updatedOrder = orderService.updateOrder(id, order);
        return ResponseEntity.ok(orderMapper.toDTO(updatedOrder));
    }

    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> countOrdersByStatus(@PathVariable String status) {
        long count = orderService.countByStatus(status);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/user/{userId}")
    public ResponseEntity<Long> countOrdersByUser(@PathVariable Integer userId) {
        long count = orderService.countByUser(userId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/revenue")
    public ResponseEntity<BigDecimal> calculateTotalRevenue(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        BigDecimal totalRevenue = orderService.calculateTotalRevenue(startDate, endDate);
        return ResponseEntity.ok(totalRevenue);
    }

    @GetMapping("/revenue/status/{status}")
    public ResponseEntity<BigDecimal> calculateTotalRevenueByStatus(@PathVariable String status) {
        BigDecimal totalRevenue = orderService.calculateTotalRevenueByStatus(status);
        return ResponseEntity.ok(totalRevenue);
    }
} 