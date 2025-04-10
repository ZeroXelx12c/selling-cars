package com.example.selling_cars.service;

import com.example.selling_cars.entity.Order;
import com.example.selling_cars.entity.OrderDetail;
import com.example.selling_cars.repository.OrderDetailRepository;
import com.example.selling_cars.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    // Lấy tất cả đơn hàng
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Lấy đơn hàng theo ID
    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    // Lấy đơn hàng theo người dùng
    public List<Order> getOrdersByUser(Integer userId) {
        return orderRepository.findByUserUserId(userId);
    }

    // Lấy đơn hàng theo trạng thái
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByOrderStatus(status);
    }

    // Lấy đơn hàng theo người dùng và trạng thái
    public List<Order> getOrdersByUserAndStatus(Integer userId, String status) {
        return orderRepository.findByUserUserIdAndOrderStatus(userId, status);
    }

    // Lấy đơn hàng theo khoảng thời gian
    public List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate);
    }

    // Lấy đơn hàng theo khoảng giá
    public List<Order> getOrdersByAmountRange(BigDecimal minAmount, BigDecimal maxAmount) {
        return orderRepository.findByTotalAmountBetween(minAmount, maxAmount);
    }

    // Lấy đơn hàng theo khu vực giao hàng
    public List<Order> getOrdersByDeliveryArea(String deliveryArea) {
        return orderRepository.findByDeliveryArea(deliveryArea);
    }

    // Lấy đơn hàng theo khoảng giá
    public List<Order> getOrdersByPriceRange(BigDecimal minAmount, BigDecimal maxAmount) {
        return orderRepository.findByTotalAmountBetween(minAmount, maxAmount);
    }

    // Tìm đơn hàng theo nhiều tiêu chí
    public Page<Order> findByFilters(
            Integer userId,
            String status,
            LocalDateTime startDate,
            LocalDateTime endDate,
            BigDecimal minAmount,
            BigDecimal maxAmount,
            String deliveryArea,
            Pageable pageable) {
        // This method should implement custom filtering based on the provided
        // parameters
        // You might need to implement a custom query in OrderRepository
        return orderRepository.findByFilters(userId, status, startDate, endDate, minAmount, maxAmount, deliveryArea,
                pageable);
    }

    // Thêm đơn hàng mới
    @Transactional
    public Order createOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("PENDING");
        return orderRepository.save(order);
    }

    // Cập nhật đơn hàng
    @Transactional
    public Order updateOrder(Integer id, Order orderDetails) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        order.setOrderStatus(orderDetails.getOrderStatus());
        order.setDeliveryAddress(orderDetails.getDeliveryAddress());
        order.setDeliveryArea(orderDetails.getDeliveryArea());
        order.setDeliveryPhone(orderDetails.getDeliveryPhone());
        order.setTotalAmount(orderDetails.getTotalAmount());
        order.setUser(orderDetails.getUser());

        return orderRepository.save(order);
    }

    // Xóa đơn hàng
    @Transactional
    public void deleteOrder(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        orderRepository.delete(order);
    }

    // Cập nhật trạng thái đơn hàng
    @Transactional
    public Order updateOrderStatus(Integer id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }

    // Đếm số lượng đơn hàng theo trạng thái
    public long countByStatus(String status) {
        return orderRepository.countByOrderStatus(status);
    }

    // Đếm số lượng đơn hàng theo người dùng
    public long countByUser(Integer userId) {
        return orderRepository.countByUserUserId(userId);
    }

    // Tính tổng doanh thu theo khoảng thời gian
    public BigDecimal calculateTotalRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.calculateTotalRevenue(startDate, endDate);
    }

    // Tính tổng doanh thu theo trạng thái
    public BigDecimal calculateTotalRevenueByStatus(String status) {
        return orderRepository.calculateTotalRevenueByStatus(status);
    }

    @Transactional
    public Order saveOrder(Order order, OrderDetail orderDetail) {
        Order savedOrder = orderRepository.save(order);
        orderDetail.setOrder(savedOrder);
        orderDetailRepository.save(orderDetail);
        return savedOrder;
    }
}