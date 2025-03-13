package com.example.selling_cars.controller;

import com.example.selling_cars.entity.Orders;
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
    public ResponseEntity<List<Orders>> getAllOrders() {
        return ResponseEntity.ok(ordersService.getAllOrders());
    }

    // Lấy đơn hàng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Integer id) {
        return ordersService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lấy đơn hàng theo user (cho người dùng)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Orders>> getOrdersByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(ordersService.getOrdersByUser(userId));
    }

    // Đếm đơn hàng mới trong ngày (cho admin dashboard)
    @GetMapping("/new-today")
    public ResponseEntity<Long> getNewOrdersToday() {
        return ResponseEntity.ok(ordersService.getNewOrdersToday());
    }

    // Lấy đơn hàng theo trạng thái (cho admin)
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Orders>> getOrdersByStatus(@PathVariable String status) {
        return ResponseEntity.ok(ordersService.getOrdersByStatus(status));
    }

    // Lấy tổng doanh thu (cho admin dashboard)
    @GetMapping("/total-revenue")
    public ResponseEntity<Double> getTotalRevenue() {
        return ResponseEntity.ok(ordersService.getTotalRevenue());
    }

    // Tạo đơn hàng mới (cho người dùng)
    @PostMapping
    public ResponseEntity<Orders> createOrder(@RequestBody Orders order) {
        return ResponseEntity.ok(ordersService.createOrder(order));
    }

    // Cập nhật đơn hàng (cho admin)
    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Integer id, @RequestBody Orders orderDetails) {
        return ResponseEntity.ok(ordersService.updateOrder(id, orderDetails));
    }

    // Xóa đơn hàng (cho admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        ordersService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/details-from-sp/{orderId}")
    public ResponseEntity<Orders> getOrderDetailsFromStoredProcedure(@PathVariable Integer orderId) {
        return ResponseEntity.ok(ordersService.getOrderDetailsFromStoredProcedure(orderId));
    }
}