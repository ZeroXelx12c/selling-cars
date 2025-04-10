package com.example.selling_cars.repository;

import com.example.selling_cars.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    // Tìm Tin Tức theo danh mục
    List<News> findByCategoryCategoryId(Integer categoryId);

    // Tìm Tin Tức theo tiêu đề chứa từ khóa
    List<News> findByTitleContainingIgnoreCase(String keyword);

    // Tìm Tin Tức theo khoảng thời gian
    List<News> findByPublishedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Tìm Tin Tức theo danh mục và khoảng thời gian
    List<News> findByCategoryCategoryIdAndPublishedDateBetween(Integer categoryId, LocalDateTime startDate, LocalDateTime endDate);

    // Tìm Tin Tức mới nhất
    List<News> findTop10ByOrderByPublishedDateDesc();

    // Tìm Tin Tức theo danh mục và sắp xếp theo ngày đăng
    Page<News> findByCategoryCategoryIdOrderByPublishedDateDesc(Integer categoryId, Pageable pageable);

    // Tìm Tin Tức theo từ khóa và sắp xếp theo ngày đăng
    Page<News> findByTitleContainingIgnoreCaseOrderByPublishedDateDesc(String keyword, Pageable pageable);

    // Đếm số lượng Tin Tức theo danh mục
    long countByCategoryCategoryId(Integer categoryId);

    // Đếm số lượng Tin Tức theo khoảng thời gian
    long countByPublishedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<News> findByStatus(String status);
} 