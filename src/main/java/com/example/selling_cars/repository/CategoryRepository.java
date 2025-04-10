package com.example.selling_cars.repository;

import com.example.selling_cars.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Tìm danh mục theo tên
    Optional<Category> findByCategoryName(String categoryName);

    // Tìm danh mục theo tên chứa từ khóa
    List<Category> findByCategoryNameContainingIgnoreCase(String keyword);

    // Kiểm tra danh mục có tồn tại theo tên
    boolean existsByCategoryName(String categoryName);

    // Lấy danh sách danh mục có Sản Phẩm
    List<Category> findByProductsIsNotNull();

    // Lấy danh sách danh mục có Tin Tức
    List<Category> findByNewsIsNotNull();

    List<Category> findByStatus(String status);
} 