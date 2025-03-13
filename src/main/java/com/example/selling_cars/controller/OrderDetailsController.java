package com.example.selling_cars.controller;

import com.example.selling_cars.entity.OrderDetails;
import com.example.selling_cars.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    // Lấy tất cả chi tiết đơn hàng (cho admin)
    @GetMapping
    public ResponseEntity<List<OrderDetails>> getAllOrderDetails() {
        return ResponseEntity.ok(orderDetailsService.getAllOrderDetails());
    }

    // Lấy chi tiết đơn hàng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetails> getOrderDetailById(@PathVariable Integer id) {
        return orderDetailsService.getOrderDetailById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lấy chi tiết đơn hàng theo OrderID (cho người dùng/admin)
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsByOrder(@PathVariable Integer orderId) {
        return ResponseEntity.ok(orderDetailsService.getOrderDetailsByOrder(orderId));
    }

    // Lấy chi tiết đơn hàng theo ProductID (cho admin)
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsByProduct(@PathVariable Integer productId) {
        return ResponseEntity.ok(orderDetailsService.getOrderDetailsByProduct(productId));
    }

    // Tính tổng số lượng sản phẩm đã bán (cho admin dashboard)
    @GetMapping("/total-sold")
    public ResponseEntity<Long> getTotalSoldQuantity() {
        return ResponseEntity.ok(orderDetailsService.getTotalSoldQuantity());
    }

    // Thêm chi tiết đơn hàng mới (cho admin hoặc tích hợp khi tạo đơn hàng)
    @PostMapping
    public ResponseEntity<OrderDetails> createOrderDetail(@RequestBody OrderDetails orderDetail) {
        return ResponseEntity.ok(orderDetailsService.createOrderDetail(orderDetail));
    }

    // Cập nhật chi tiết đơn hàng (cho admin)
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetails> updateOrderDetail(@PathVariable Integer id, @RequestBody OrderDetails orderDetailDetails) {
        return ResponseEntity.ok(orderDetailsService.updateOrderDetail(id, orderDetailDetails));
    }

    // Xóa chi tiết đơn hàng (cho admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Integer id) {
        orderDetailsService.deleteOrderDetail(id);
        return ResponseEntity.noContent().build();
    }
}