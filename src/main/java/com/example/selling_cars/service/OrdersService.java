package com.example.selling_cars.service;

import com.example.selling_cars.dto.OrderDTO;
import com.example.selling_cars.dto.OrderDetailDTO;
import com.example.selling_cars.entity.Orders;
import com.example.selling_cars.repository.OrdersRepository;
import com.example.selling_cars.repository.ProductsRepository;
import com.example.selling_cars.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProductsRepository productsRepository;

    // Lấy tất cả đơn hàng
    public List<OrderDTO> getAllOrders() {
        return ordersRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Lấy đơn hàng theo ID
    public Optional<OrderDTO> getOrderById(Integer id) {
        return ordersRepository.findById(id).map(this::convertToDTO);
    }

    // Lấy đơn hàng theo người dùng
    public List<OrderDTO> getOrdersByUser(Integer userId) {
        return ordersRepository.findByUserUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy đơn hàng theo trạng thái
    public List<OrderDTO> getOrdersByStatus(String status) {
        return ordersRepository.findByOrderStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Đếm đơn hàng mới trong ngày
    public long getNewOrdersToday() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        return ordersRepository.countNewOrdersToday(startOfDay);
    }

    // Tạo đơn hàng mới
    public OrderDTO createOrder(OrderDTO orderDTO) {
        usersRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));
        productsRepository.findById(orderDTO.getOrderDetails().get(0).getProductId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));

        ordersRepository.createOrder(
                orderDTO.getUserId(),
                orderDTO.getOrderDetails().get(0).getProductId(),
                orderDTO.getOrderDetails().get(0).getExteriorOptionId(),
                orderDTO.getOrderDetails().get(0).getInteriorOptionId(),
                orderDTO.getDeliveryArea(),
                orderDTO.getDepositAmount() / orderDTO.getTotalAmount() * 100 // Chuyển thành phần trăm
        );

        // Lấy đơn hàng vừa tạo (giả sử OrderID tăng dần)
        List<Orders> allOrders = ordersRepository.findAll();
        Orders latestOrder = allOrders.get(allOrders.size() - 1);
        return convertToDTO(latestOrder);
    }

    // Ghi thanh toán cho đơn hàng
    public void recordPayment(Integer orderId, Double amount, String paymentMethod, String transactionId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng!"));
        ordersRepository.recordPayment(orderId, amount, paymentMethod, transactionId);
    }

    // Cập nhật trạng thái đơn hàng
    public OrderDTO updateOrderStatus(Integer id, String status) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng!"));

        order.setOrderStatus(status);
        Orders updatedOrder = ordersRepository.save(order);
        return convertToDTO(updatedOrder);
    }

    // Xóa đơn hàng
    public void deleteOrder(Integer id) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng!"));
        ordersRepository.delete(order);
    }

    // Chuyển từ Entity sang DTO
    private OrderDTO convertToDTO(Orders order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setUserId(order.getUser().getUserId());
        dto.setUserFullName(order.getUser().getFullName());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setDepositAmount(order.getDepositAmount());
        dto.setDeliveryArea(order.getDeliveryArea());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setNotes(order.getNotes());

        // Thêm thông tin chi tiết đơn hàng nếu cần
        if (order.getOrderDetails() != null) {
            dto.setOrderDetails(order.getOrderDetails().stream()
                    .map(od -> {
                        OrderDetailDTO detailDTO = new OrderDetailDTO();
                        detailDTO.setOrderDetailId(od.getOrderDetailId());
                        detailDTO.setProductId(od.getProduct().getProductId());
                        detailDTO.setProductName(od.getProduct().getProductName());
                        detailDTO.setQuantity(od.getQuantity());
                        detailDTO.setUnitPrice(od.getUnitPrice());
                        return detailDTO;
                    }).collect(Collectors.toList()));
        }

        // Thêm thông tin thanh toán nếu có
        if (order.getPayment() != null) {
            dto.setPaymentStatus("Paid");
            dto.setTransactionId(order.getPayment().getTransactionId());
        } else {
            dto.setPaymentStatus("Pending");
        }

        return dto;
    }
}