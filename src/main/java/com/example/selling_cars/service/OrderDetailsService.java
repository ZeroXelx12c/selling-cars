package com.example.selling_cars.service;

import com.example.selling_cars.entity.OrderDetails;
import com.example.selling_cars.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    // Lấy tất cả chi tiết đơn hàng
    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }

    // Lấy chi tiết đơn hàng theo ID
    public Optional<OrderDetails> getOrderDetailById(Integer id) {
        return orderDetailsRepository.findById(id);
    }

    // Lấy chi tiết đơn hàng theo OrderID
    public List<OrderDetails> getOrderDetailsByOrder(Integer orderId) {
        return orderDetailsRepository.findByOrderOrderId(orderId);
    }

    // Lấy chi tiết đơn hàng theo ProductID
    public List<OrderDetails> getOrderDetailsByProduct(Integer productId) {
        return orderDetailsRepository.findByProductProductId(productId);
    }

    // Tính tổng số lượng sản phẩm đã bán
    public Long getTotalSoldQuantity() {
        Long total = orderDetailsRepository.getTotalSoldQuantity();
        return total != null ? total : 0L;
    }

    // Thêm chi tiết đơn hàng mới
    public OrderDetails createOrderDetail(OrderDetails orderDetail) {
        return orderDetailsRepository.save(orderDetail);
    }

    // Cập nhật chi tiết đơn hàng
    public OrderDetails updateOrderDetail(Integer id, OrderDetails orderDetailDetails) {
        OrderDetails orderDetail = orderDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết đơn hàng!"));

        orderDetail.setOrder(orderDetailDetails.getOrder());
        orderDetail.setProduct(orderDetailDetails.getProduct());
        orderDetail.setQuantity(orderDetailDetails.getQuantity());
        orderDetail.setUnitPrice(orderDetailDetails.getUnitPrice());

        return orderDetailsRepository.save(orderDetail);
    }

    // Xóa chi tiết đơn hàng
    public void deleteOrderDetail(Integer id) {
        OrderDetails orderDetail = orderDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết đơn hàng!"));
        orderDetailsRepository.delete(orderDetail);
    }
}