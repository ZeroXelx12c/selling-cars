package com.example.selling_cars.repository;

import com.example.selling_cars.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {
    // Tìm sản phẩm theo tên (gần đúng)
    List<Products> findByProductNameContainingIgnoreCase(String productName);

    // Tìm sản phẩm theo danh mục
    List<Products> findByCategoryCategoryId(Integer categoryId);

    // Đếm số sản phẩm tồn kho
    @Query("SELECT COUNT(p) FROM Products p WHERE p.status = 'InStock'")
    long countInStockProducts();

    // Lấy danh sách sản phẩm nổi bật (giả sử dựa trên ngày tạo gần nhất)
    @Query("SELECT p FROM Products p WHERE p.status = 'InStock' ORDER BY p.createdAt DESC")
    List<Products> findFeaturedProducts();

    @Query(value = "EXEC GetProducts @CategoryID = :categoryId", nativeQuery = true)
    List<Object[]> getProductsNative(@Param("categoryId") Integer categoryId);
}