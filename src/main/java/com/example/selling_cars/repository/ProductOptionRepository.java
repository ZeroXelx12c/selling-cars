package com.example.selling_cars.repository;

import com.example.selling_cars.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Integer> {
    // Tìm tùy chọn theo sản phẩm
    List<ProductOption> findByProductProductId(Integer productId);

    // Tìm tùy chọn theo loại
    List<ProductOption> findByOptionType(String optionType);

    // Tìm tùy chọn theo sản phẩm và loại
    List<ProductOption> findByProductProductIdAndOptionType(Integer productId, String optionType);

    // Tìm tùy chọn theo tên
    Optional<ProductOption> findByOptionName(String optionName);

    // Tìm tùy chọn theo sản phẩm và tên
    Optional<ProductOption> findByProductProductIdAndOptionName(Integer productId, String optionName);

    // Kiểm tra tùy chọn có tồn tại theo tên và sản phẩm
    boolean existsByProductProductIdAndOptionName(Integer productId, String optionName);

    // Lấy danh sách tùy chọn nội thất của sản phẩm
    List<ProductOption> findByProductProductIdAndOptionTypeOrderByAdditionalPriceAsc(Integer productId, String optionType);
} 