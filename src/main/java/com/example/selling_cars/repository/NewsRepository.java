package com.example.selling_cars.repository;

import com.example.selling_cars.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    // Tìm tin tức theo tiêu đề (gần đúng)
    List<News> findByTitleContainingIgnoreCase(String title);

    // Lấy tin tức theo danh mục
    List<News> findByCategoryCategoryId(Integer categoryId);

    // Lấy danh sách tin tức mới nhất
    @Query("SELECT n FROM News n ORDER BY n.publishedDate DESC")
    List<News> findLatestNews();

    // Đếm số lượng tin tức theo danh mục
    @Query("SELECT COUNT(n) FROM News n WHERE n.category.categoryId = :categoryId")
    long countNewsByCategory(Integer categoryId);

    // Gọi stored procedure GetNews
    @Query(value = "EXEC GetNews", nativeQuery = true)
    List<Object[]> getNewsNative();
}