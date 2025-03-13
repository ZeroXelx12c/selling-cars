package com.example.selling_cars.repository;

import com.example.selling_cars.entity.ProductOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionsRepository extends JpaRepository<ProductOptions, Integer> {
    // Lấy tùy chọn theo sản phẩm
    List<ProductOptions> findByProductProductId(Integer productId);

    // Lấy tùy chọn theo loại (Exterior/Interior) và sản phẩm
    List<ProductOptions> findByProductProductIdAndOptionType(Integer productId, String optionType);

    // Đếm số lượng tùy chọn theo sản phẩm
    @Query("SELECT COUNT(po) FROM ProductOptions po WHERE po.product.productId = :productId")
    long countOptionsByProductId(Integer productId);
}