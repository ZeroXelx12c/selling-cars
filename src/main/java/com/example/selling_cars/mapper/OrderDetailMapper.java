package com.example.selling_cars.mapper;

import com.example.selling_cars.dto.OrderDetailDTO;
import com.example.selling_cars.entity.OrderDetail;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailMapper {
    
    public OrderDetailDTO toDTO(OrderDetail orderDetail) {
        if (orderDetail == null) {
            return null;
        }
        
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setOrderDetailId(orderDetail.getOrderDetailId());
        dto.setQuantity(orderDetail.getQuantity());
        dto.setUnitPrice(orderDetail.getUnitPrice());
        
        // Calculate total price based on quantity and unit price
        if (orderDetail.getQuantity() != null && orderDetail.getUnitPrice() != null) {
            dto.setTotalPrice(orderDetail.getUnitPrice().multiply(new java.math.BigDecimal(orderDetail.getQuantity())));
        }
        
        if (orderDetail.getOrder() != null) {
            dto.setOrderId(orderDetail.getOrder().getOrderId());
        }
        
        if (orderDetail.getProduct() != null) {
            dto.setProductId(orderDetail.getProduct().getProductId());
        }
        
        if (orderDetail.getExteriorOption() != null) {
            dto.setExteriorOptionId(orderDetail.getExteriorOption().getOptionId());
        }
        
        if (orderDetail.getInteriorOption() != null) {
            dto.setInteriorOptionId(orderDetail.getInteriorOption().getOptionId());
        }
        
        return dto;
    }

    public OrderDetail toEntity(OrderDetailDTO dto) {
        if (dto == null) {
            return null;
        }
        
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailId(dto.getOrderDetailId());
        orderDetail.setQuantity(dto.getQuantity());
        orderDetail.setUnitPrice(dto.getUnitPrice());
        
        // Note: Total price is calculated in the service layer or database
        
        return orderDetail;
    }
} 