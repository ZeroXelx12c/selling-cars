package com.example.selling_cars.service.impl;

import com.example.selling_cars.entity.OrderDetail;
import com.example.selling_cars.exception.ResourceNotFoundException;
import com.example.selling_cars.repository.OrderDetailRepository;
import com.example.selling_cars.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    @Override
    public Optional<OrderDetail> getOrderDetailById(Integer id) {
        return orderDetailRepository.findById(id);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrder(Integer orderId) {
        return orderDetailRepository.findByOrderOrderId(orderId);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByProduct(Integer productId) {
        return orderDetailRepository.findByProductProductId(productId);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByExteriorOption(Integer optionId) {
        return orderDetailRepository.findByExteriorOptionOptionId(optionId);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByInteriorOption(Integer optionId) {
        return orderDetailRepository.findByInteriorOptionOptionId(optionId);
    }

    @Override
    @Transactional
    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    @Transactional
    public OrderDetail updateOrderDetail(Integer id, OrderDetail orderDetailDetails) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderDetail", "id", id));

        // Cập nhật thông tin
        orderDetail.setOrder(orderDetailDetails.getOrder());
        orderDetail.setProduct(orderDetailDetails.getProduct());
        orderDetail.setQuantity(orderDetailDetails.getQuantity());
        orderDetail.setUnitPrice(orderDetailDetails.getUnitPrice());
        orderDetail.setExteriorOption(orderDetailDetails.getExteriorOption());
        orderDetail.setInteriorOption(orderDetailDetails.getInteriorOption());

        return orderDetailRepository.save(orderDetail);
    }

    @Override
    @Transactional
    public void deleteOrderDetail(Integer id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderDetail", "id", id));
        orderDetailRepository.delete(orderDetail);
    }

    @Override
    public long countByOrder(Integer orderId) {
        return orderDetailRepository.countByOrderOrderId(orderId);
    }

    @Override
    public long countByProduct(Integer productId) {
        return orderDetailRepository.countByProductProductId(productId);
    }
} 