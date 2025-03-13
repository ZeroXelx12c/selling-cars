package com.example.selling_cars.controller;

import com.example.selling_cars.dto.OrderDetailDTO;
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
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
        return ResponseEntity.ok(orderDetailsService.getAllOrderDetails());
    }

    // Lấy chi tiết đơn hàng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> getOrderDetailById(@PathVariable Integer id) {
        return orderDetailsService.getOrderDetailById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lấy chi tiết đơn hàng theo đơn hàng (cho người dùng/admin)
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderDetailDTO>> getOrderDetailsByOrder(@PathVariable Integer orderId) {
        return ResponseEntity.ok(orderDetailsService.getOrderDetailsByOrder(orderId));
    }

    // Lấy chi tiết đơn hàng theo sản phẩm (cho admin)
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<OrderDetailDTO>> getOrderDetailsByProduct(@PathVariable Integer productId) {
        return ResponseEntity.ok(orderDetailsService.getOrderDetailsByProduct(productId));
    }

    // Đếm số lượng sản phẩm đã bán (cho admin)
    @GetMapping("/sold-quantity/product/{productId}")
    public ResponseEntity<Long> getSoldQuantityByProduct(@PathVariable Integer productId) {
        return ResponseEntity.ok(orderDetailsService.getSoldQuantityByProduct(productId));
    }

    // Thêm chi tiết đơn hàng mới (cho admin hoặc tích hợp với Orders)
    @PostMapping
    public ResponseEntity<OrderDetailDTO> createOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        return ResponseEntity.ok(orderDetailsService.createOrderDetail(orderDetailDTO));
    }

    // Cập nhật chi tiết đơn hàng (cho admin)
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> updateOrderDetail(@PathVariable Integer id, @RequestBody OrderDetailDTO orderDetailDTO) {
        return ResponseEntity.ok(orderDetailsService.updateOrderDetail(id, orderDetailDTO));
    }

    // Xóa chi tiết đơn hàng (cho admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Integer id) {
        orderDetailsService.deleteOrderDetail(id);
        return ResponseEntity.noContent().build();
    }
}