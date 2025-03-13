package com.example.selling_cars.repository;

import com.example.selling_cars.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
    // Tìm danh mục theo tên (gần đúng)
    Optional<Categories> findByCategoryNameIgnoreCase(String categoryName);

    // Đếm số lượng danh mục
    @Query("SELECT COUNT(c) FROM Categories c")
    long countCategories();

    // Tìm tất cả danh mục có sản phẩm tồn kho
    @Query("SELECT DISTINCT c FROM Categories c JOIN c.products p WHERE p.status = 'InStock'")
    List<Categories> findCategoriesWithInStockProducts();
}