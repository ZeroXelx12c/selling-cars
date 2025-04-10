package com.example.selling_cars.controller;

import com.example.selling_cars.dto.NewsDTO;
import com.example.selling_cars.entity.News;
import com.example.selling_cars.mapper.NewsMapper;
import com.example.selling_cars.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/news")
@CrossOrigin(origins = "*")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsMapper newsMapper;

    @GetMapping
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        List<News> newsList = newsService.getAllNews();
        List<NewsDTO> newsDTOs = newsList.stream()
                .map(newsMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(newsDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getNewsById(@PathVariable Integer id) {
        News news = newsService.getNewsById(id);
        return ResponseEntity.ok(newsMapper.toDTO(news));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<NewsDTO>> getNewsByCategory(@PathVariable Integer categoryId) {
        List<News> newsList = newsService.getNewsByCategory(categoryId);
        List<NewsDTO> newsDTOs = newsList.stream()
                .map(newsMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(newsDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<NewsDTO>> searchNews(@RequestParam String keyword) {
        List<News> newsList = newsService.searchNews(keyword);
        List<NewsDTO> newsDTOs = newsList.stream()
                .map(newsMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(newsDTOs);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<NewsDTO>> getLatestNews() {
        List<News> newsList = newsService.getLatestNews();
        List<NewsDTO> newsDTOs = newsList.stream()
                .map(newsMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(newsDTOs);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<NewsDTO>> getNewsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<News> newsList = newsService.getNewsByDateRange(startDate, endDate);
        List<NewsDTO> newsDTOs = newsList.stream()
                .map(newsMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(newsDTOs);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<NewsDTO>> getPagedNews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "publishedDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        Page<News> newsPage = newsService.getPagedNews(pageable);
        Page<NewsDTO> newsDTOPage = newsPage.map(newsMapper::toDTO);
        return ResponseEntity.ok(newsDTOPage);
    }

    @GetMapping("/category/{categoryId}/paged")
    public ResponseEntity<Page<NewsDTO>> getPagedNewsByCategory(
            @PathVariable Integer categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "publishedDate"));
        
        Page<News> newsPage = newsService.getPagedNewsByCategory(categoryId, pageable);
        Page<NewsDTO> newsDTOPage = newsPage.map(newsMapper::toDTO);
        return ResponseEntity.ok(newsDTOPage);
    }

    @PostMapping
    public ResponseEntity<NewsDTO> createNews(@Valid @RequestBody NewsDTO newsDTO) {
        News news = newsMapper.toEntity(newsDTO);
        News savedNews = newsService.createNews(news);
        return new ResponseEntity<>(newsMapper.toDTO(savedNews), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsDTO> updateNews(
            @PathVariable Integer id,
            @Valid @RequestBody NewsDTO newsDTO) {
        News news = newsMapper.toEntity(newsDTO);
        News updatedNews = newsService.updateNews(id, news);
        return ResponseEntity.ok(newsMapper.toDTO(updatedNews));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Integer id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<NewsDTO> updateNewsStatus(
            @PathVariable Integer id,
            @RequestParam String status) {
        News updatedNews = newsService.updateNewsStatus(id, status);
        return ResponseEntity.ok(newsMapper.toDTO(updatedNews));
    }

    @GetMapping("/count/category/{categoryId}")
    public ResponseEntity<Long> countNewsByCategory(@PathVariable Integer categoryId) {
        long count = newsService.countNewsByCategory(categoryId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/date-range")
    public ResponseEntity<Long> countNewsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        long count = newsService.countNewsByDateRange(startDate, endDate);
        return ResponseEntity.ok(count);
    }
} 