package com.example.selling_cars.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.selling_cars.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>,JpaSpecificationExecutor<Product> {
    // Tìm Sản Phẩm theo tên
    Optional<Product> findByProductName(String productName);

    // Tìm Sản Phẩm theo tên chứa từ khóa
    List<Product> findByProductNameContainingIgnoreCase(String keyword);

    // Tìm Sản Phẩm theo danh mục
    List<Product> findByCategoryCategoryId(Integer categoryId);

    // Tìm Sản Phẩm theo khoảng giá
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // Tìm Sản Phẩm theo năm sản xuất
    List<Product> findByManufactureYear(Integer year);

    // Tìm Sản Phẩm theo trạng thái
    List<Product> findByStatus(String status);

    // Tìm Sản Phẩm theo model
    List<Product> findByModel(String model);

    // Tìm Sản Phẩm theo số km đã chạy
    List<Product> findByMileageLessThanEqual(Integer maxMileage);

    // Tìm Sản Phẩm theo năm sản xuất trong khoảng
    List<Product> findByManufactureYearBetween(Integer minYear, Integer maxYear);

    // Tìm Sản Phẩm theo model chứa từ khóa
    List<Product> findByModelContainingIgnoreCase(String model);

    // Tìm Sản Phẩm theo nhiều tiêu chí
    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR p.category.categoryId = :categoryId) AND " +
            "(:status IS NULL OR p.status = :status) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:minYear IS NULL OR p.manufactureYear >= :minYear) AND " +
            "(:maxYear IS NULL OR p.manufactureYear <= :maxYear) AND " +
            "(:model IS NULL OR LOWER(p.model) LIKE LOWER(CONCAT('%', :model, '%')))")
    Page<Product> findByFilters(
            @Param("categoryId") Integer categoryId,
            @Param("status") String status,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minYear") Integer minYear,
            @Param("maxYear") Integer maxYear,
            @Param("model") String model,
            Pageable pageable);

    // Đếm số lượng Sản Phẩm theo trạng thái
    long countByStatus(String status);

    // Đếm số lượng Sản Phẩm theo danh mục
    long countByCategoryCategoryId(Integer categoryId);

    Page<Product> findByProductNameContainingIgnoreCase(String keyword, Pageable pageable);
}