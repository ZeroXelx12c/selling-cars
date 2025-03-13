package com.example.selling_cars.controller;

import com.example.selling_cars.dto.NewsDTO;
import com.example.selling_cars.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    // Lấy tất cả tin tức (cho giao diện người dùng)
    @GetMapping
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    // Lấy tin tức theo ID
    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getNewsById(@PathVariable Integer id) {
        return newsService.getNewsById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tìm kiếm tin tức theo tiêu đề
    @GetMapping("/search")
    public ResponseEntity<List<NewsDTO>> searchNewsByTitle(@RequestParam String title) {
        return ResponseEntity.ok(newsService.searchNewsByTitle(title));
    }

    // Lấy tin tức theo danh mục
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<NewsDTO>> getNewsByCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(newsService.getNewsByCategory(categoryId));
    }

    // Lấy tin tức mới nhất (cho giao diện người dùng)
    @GetMapping("/latest")
    public ResponseEntity<List<NewsDTO>> getLatestNews() {
        return ResponseEntity.ok(newsService.getLatestNews());
    }

    // Đếm số lượng tin tức theo danh mục (cho admin)
    @GetMapping("/count/category/{categoryId}")
    public ResponseEntity<Long> getNewsCountByCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(newsService.getNewsCountByCategory(categoryId));
    }

    // Thêm tin tức mới (cho admin)
    @PostMapping
    public ResponseEntity<NewsDTO> createNews(@RequestBody NewsDTO newsDTO) {
        return ResponseEntity.ok(newsService.createNews(newsDTO));
    }

    // Cập nhật tin tức (cho admin)
    @PutMapping("/{id}")
    public ResponseEntity<NewsDTO> updateNews(@PathVariable Integer id, @RequestBody NewsDTO newsDTO) {
        return ResponseEntity.ok(newsService.updateNews(id, newsDTO));
    }

    // Xóa tin tức (cho admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Integer id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }
}