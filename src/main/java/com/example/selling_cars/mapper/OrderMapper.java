package com.example.selling_cars.mapper;

import com.example.selling_cars.dto.OrderDTO;
import com.example.selling_cars.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {
    
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    
    public OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }
        
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setOrderDate(order.getOrderDate());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setDeliveryArea(order.getDeliveryArea());
        dto.setDeliveryPhone(order.getDeliveryPhone());
        dto.setTotalAmount(order.getTotalAmount());
        
        if (order.getUser() != null) {
            dto.setUserId(order.getUser().getUserId());
        }
        
        if (order.getOrderDetails() != null) {
            dto.setOrderDetails(order.getOrderDetails().stream()
                    .map(orderDetailMapper::toDTO)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }

    public Order toEntity(OrderDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Order order = new Order();
        order.setOrderId(dto.getOrderId());
        order.setOrderDate(dto.getOrderDate());
        order.setOrderStatus(dto.getOrderStatus());
        order.setDeliveryAddress(dto.getDeliveryAddress());
        order.setDeliveryArea(dto.getDeliveryArea());
        order.setDeliveryPhone(dto.getDeliveryPhone());
        order.setTotalAmount(dto.getTotalAmount());
        
        if (dto.getOrderDetails() != null) {
            order.setOrderDetails(dto.getOrderDetails().stream()
                    .map(orderDetailMapper::toEntity)
                    .collect(Collectors.toList()));
        }
        
        return order;
    }
} 