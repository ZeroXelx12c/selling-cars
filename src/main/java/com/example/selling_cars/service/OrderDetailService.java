package com.example.selling_cars.service;

import com.example.selling_cars.entity.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
    
    List<OrderDetail> getAllOrderDetails();
    
    Optional<OrderDetail> getOrderDetailById(Integer id);
    
    List<OrderDetail> getOrderDetailsByOrder(Integer orderId);
    
    List<OrderDetail> getOrderDetailsByProduct(Integer productId);
    
    List<OrderDetail> getOrderDetailsByExteriorOption(Integer optionId);
    
    List<OrderDetail> getOrderDetailsByInteriorOption(Integer optionId);
    
    OrderDetail createOrderDetail(OrderDetail orderDetail);
    
    OrderDetail updateOrderDetail(Integer id, OrderDetail orderDetail);
    
    void deleteOrderDetail(Integer id);
    
    long countByOrder(Integer orderId);
    
    long countByProduct(Integer productId);
} 