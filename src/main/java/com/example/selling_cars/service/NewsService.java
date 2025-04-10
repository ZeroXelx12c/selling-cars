package com.example.selling_cars.service;

import com.example.selling_cars.entity.News;
import com.example.selling_cars.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    // Lấy tất cả Tin Tức
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    // Lấy Tin Tức theo ID
    public News getNewsById(Integer id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + id));
    }

    // Lấy Tin Tức theo danh mục
    public List<News> getNewsByCategory(Integer categoryId) {
        return newsRepository.findByCategoryCategoryId(categoryId);
    }

    // Tìm Tin Tức theo từ khóa
    public List<News> searchNews(String keyword) {
        return newsRepository.findByTitleContainingIgnoreCase(keyword);
    }

    // Lấy Tin Tức theo khoảng thời gian
    public List<News> getNewsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return newsRepository.findByPublishedDateBetween(startDate, endDate);
    }

    // Lấy Tin Tức theo danh mục và khoảng thời gian
    public List<News> getNewsByCategoryAndDateRange(Integer categoryId, LocalDateTime startDate, LocalDateTime endDate) {
        return newsRepository.findByCategoryCategoryIdAndPublishedDateBetween(categoryId, startDate, endDate);
    }

    // Lấy Tin Tức mới nhất
    public List<News> getLatestNews() {
        return newsRepository.findTop10ByOrderByPublishedDateDesc();
    }

    // Lấy Tin Tức theo danh mục và sắp xếp theo ngày đăng
    public Page<News> getNewsByCategoryOrderByDate(Integer categoryId, Pageable pageable) {
        return newsRepository.findByCategoryCategoryIdOrderByPublishedDateDesc(categoryId, pageable);
    }

    // Lấy Tin Tức theo từ khóa và sắp xếp theo ngày đăng
    public Page<News> searchNewsOrderByDate(String keyword, Pageable pageable) {
        return newsRepository.findByTitleContainingIgnoreCaseOrderByPublishedDateDesc(keyword, pageable);
    }

    // Thêm Tin Tức mới
    @Transactional
    public News createNews(News news) {
        news.setPublishedDate(LocalDateTime.now());
        return newsRepository.save(news);
    }

    // Cập nhật Tin Tức
    @Transactional
    public News updateNews(Integer id, News newsDetails) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + id));

        news.setTitle(newsDetails.getTitle());
        news.setContent(newsDetails.getContent());
        news.setSummary(newsDetails.getSummary());
        news.setImageUrl(newsDetails.getImageUrl());
        news.setCategory(newsDetails.getCategory());
        news.setStatus(newsDetails.getStatus());

        return newsRepository.save(news);
    }

    // Xóa Tin Tức
    @Transactional
    public void deleteNews(Integer id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + id));
        newsRepository.delete(news);
    }

    // Cập nhật trạng thái Tin Tức
    @Transactional
    public News updateNewsStatus(Integer id, String status) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + id));
        news.setStatus(status);
        return newsRepository.save(news);
    }

    // Đếm số lượng Tin Tức theo danh mục
    public long countNewsByCategory(Integer categoryId) {
        return newsRepository.countByCategoryCategoryId(categoryId);
    }

    // Đếm số lượng Tin Tức theo khoảng thời gian
    public long countNewsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return newsRepository.countByPublishedDateBetween(startDate, endDate);
    }

    // Lấy Tin Tức theo trạng thái
    public List<News> getNewsByStatus(String status) {
        return newsRepository.findByStatus(status);
    }

    // Lấy Tin Tức phân trang
    public Page<News> getPagedNews(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    // Lấy Tin Tức theo danh mục phân trang
    public Page<News> getPagedNewsByCategory(Integer categoryId, Pageable pageable) {
        return newsRepository.findByCategoryCategoryIdOrderByPublishedDateDesc(categoryId, pageable);
    }
} 