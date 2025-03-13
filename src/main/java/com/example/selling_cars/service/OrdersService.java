package com.example.selling_cars.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.selling_cars.entity.OrderDetails;
import com.example.selling_cars.entity.Orders;
import com.example.selling_cars.entity.Products;
import com.example.selling_cars.entity.Users;
import com.example.selling_cars.repository.OrdersRepository;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    // Lấy tất cả đơn hàng
    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    // Lấy đơn hàng theo ID
    public Optional<Orders> getOrderById(Integer id) {
        return ordersRepository.findById(id);
    }

    // Lấy đơn hàng theo user
    public List<Orders> getOrdersByUser(Integer userId) {
        return ordersRepository.findByUserUserId(userId);
    }

    // Đếm đơn hàng mới trong ngày
    public long getNewOrdersToday() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        return ordersRepository.countNewOrdersToday(startOfDay);
    }

    // Lấy đơn hàng theo trạng thái
    public List<Orders> getOrdersByStatus(String status) {
        return ordersRepository.findByOrderStatus(status);
    }

    // Lấy tổng doanh thu
    public Double getTotalRevenue() {
        Double revenue = ordersRepository.getTotalRevenue();
        return revenue != null ? revenue : 0.0;
    }

    // Tạo đơn hàng mới
    public Orders createOrder(Orders order) {
        return ordersRepository.save(order);
    }

    // Cập nhật đơn hàng
    public Orders updateOrder(Integer id, Orders orderDetails) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng!"));

        order.setUser(orderDetails.getUser());
        order.setTotalAmount(orderDetails.getTotalAmount());
        order.setPaymentMethod(orderDetails.getPaymentMethod());
        order.setPaymentStatus(orderDetails.getPaymentStatus());
        order.setOrderStatus(orderDetails.getOrderStatus());
        order.setNotes(orderDetails.getNotes());

        return ordersRepository.save(order);
    }

    // Xóa đơn hàng
    public void deleteOrder(Integer id) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng!"));
        ordersRepository.delete(order);
    }

    public Orders getOrderDetailsFromStoredProcedure(Integer orderId) {
        List<Object[]> results = ordersRepository.getOrderDetailsNative(orderId);
        if (results.isEmpty()) {
            throw new RuntimeException("Không tìm thấy chi tiết đơn hàng!");
        }

        Object[] row = results.get(0); // Lấy dòng đầu tiên để ánh xạ Orders
        Orders order = new Orders();
        order.setOrderId((Integer) row[0]);
        Users user = new Users();
        user.setUserId((Integer) row[1]);
        user.setFullName((String) row[2]);
        order.setUser(user);
        order.setOrderDate(((Timestamp) row[3]).toLocalDateTime());
        order.setTotalAmount(((BigDecimal) row[4]).doubleValue());
        order.setPaymentMethod((String) row[5]);
        order.setPaymentStatus((String) row[6]);
        order.setOrderStatus((String) row[7]);
        order.setNotes((String) row[8]);

    // Ánh xạ danh sách OrderDetails (nếu cần)
        List<OrderDetails> details = results.stream().map(r -> {
            OrderDetails detail = new OrderDetails();
            detail.setProduct(new Products()); // Cần entity Products đầy đủ để ánh xạ
            detail.getProduct().setProductId((Integer) r[9]);
            detail.getProduct().setProductName((String) r[10]);
            detail.setQuantity((Integer) r[11]);
            detail.setUnitPrice(((BigDecimal) r[12]).doubleValue());
            return detail;
        }).collect(Collectors.toList());
        order.setOrderDetails(details);

        return order;
    }
}