package com.example.selling_cars.repository;

import com.example.selling_cars.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
    // Lấy chi tiết đơn hàng theo đơn hàng
    List<OrderDetails> findByOrderOrderId(Integer orderId);

    // Lấy chi tiết đơn hàng theo sản phẩm
    List<OrderDetails> findByProductProductId(Integer productId);

    // Đếm số lượng sản phẩm đã bán
    @Query("SELECT SUM(od.quantity) FROM OrderDetails od WHERE od.product.productId = :productId")
    Long countSoldQuantityByProduct(Integer productId);
}