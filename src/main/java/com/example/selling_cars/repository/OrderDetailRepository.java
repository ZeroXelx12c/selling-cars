package com.example.selling_cars.repository;

import com.example.selling_cars.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    // Tìm chi tiết đơn hàng theo đơn hàng
    List<OrderDetail> findByOrderOrderId(Integer orderId);

    // Tìm chi tiết đơn hàng theo sản phẩm
    List<OrderDetail> findByProductProductId(Integer productId);

    // Tìm chi tiết đơn hàng theo đơn hàng và sản phẩm
    List<OrderDetail> findByOrderOrderIdAndProductProductId(Integer orderId, Integer productId);

    // Tìm chi tiết đơn hàng theo tùy chọn ngoại thất
    List<OrderDetail> findByExteriorOptionOptionId(Integer optionId);

    // Tìm chi tiết đơn hàng theo tùy chọn nội thất
    List<OrderDetail> findByInteriorOptionOptionId(Integer optionId);

    // Tìm chi tiết đơn hàng theo đơn hàng và tùy chọn ngoại thất
    List<OrderDetail> findByOrderOrderIdAndExteriorOptionOptionId(Integer orderId, Integer optionId);

    // Tìm chi tiết đơn hàng theo đơn hàng và tùy chọn nội thất
    List<OrderDetail> findByOrderOrderIdAndInteriorOptionOptionId(Integer orderId, Integer optionId);

    // Đếm số lượng chi tiết đơn hàng theo đơn hàng
    long countByOrderOrderId(Integer orderId);

    // Đếm số lượng chi tiết đơn hàng theo sản phẩm
    long countByProductProductId(Integer productId);
} 