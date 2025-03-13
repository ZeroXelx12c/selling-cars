package com.example.selling_cars.controller;

import com.example.selling_cars.dto.OrderDTO;
import com.example.selling_cars.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    // Lấy tất cả đơn hàng (cho admin)
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(ordersService.getAllOrders());
    }

    // Lấy đơn hàng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Integer id) {
        return ordersService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lấy đơn hàng theo người dùng (cho người dùng)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(ordersService.getOrdersByUser(userId));
    }

    // Lấy đơn hàng theo trạng thái (cho admin)
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@PathVariable String status) {
        return ResponseEntity.ok(ordersService.getOrdersByStatus(status));
    }

    // Đếm đơn hàng mới trong ngày (cho admin)
    @GetMapping("/new-today")
    public ResponseEntity<Long> getNewOrdersToday() {
        return ResponseEntity.ok(ordersService.getNewOrdersToday());
    }

    // Tạo đơn hàng mới (cho người dùng)
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(ordersService.createOrder(orderDTO));
    }

    // Ghi thanh toán cho đơn hàng
    @PostMapping("/{id}/payment")
    public ResponseEntity<Void> recordPayment(
            @PathVariable Integer id,
            @RequestParam Double amount,
            @RequestParam String paymentMethod,
            @RequestParam String transactionId) {
        ordersService.recordPayment(id, amount, paymentMethod, transactionId);
        return ResponseEntity.ok().build();
    }

    // Cập nhật trạng thái đơn hàng (cho admin)
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Integer id, @RequestParam String status) {
        return ResponseEntity.ok(ordersService.updateOrderStatus(id, status));
    }

    // Xóa đơn hàng (cho admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        ordersService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}