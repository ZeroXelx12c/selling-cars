package com.example.selling_cars.service;

import com.example.selling_cars.dto.NewsDTO;
import com.example.selling_cars.entity.Categories;
import com.example.selling_cars.entity.News;
import com.example.selling_cars.repository.CategoriesRepository;
import com.example.selling_cars.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    // Lấy tất cả tin tức
    public List<NewsDTO> getAllNews() {
        return newsRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Lấy tin tức theo ID
    public Optional<NewsDTO> getNewsById(Integer id) {
        return newsRepository.findById(id).map(this::convertToDTO);
    }

    // Tìm tin tức theo tiêu đề
    public List<NewsDTO> searchNewsByTitle(String title) {
        return newsRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy tin tức theo danh mục
    public List<NewsDTO> getNewsByCategory(Integer categoryId) {
        return newsRepository.findByCategoryCategoryId(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy tin tức mới nhất
    public List<NewsDTO> getLatestNews() {
        List<Object[]> results = newsRepository.getNewsNative();
        return results.stream().map(row -> {
            NewsDTO dto = new NewsDTO();
            dto.setNewsId((Integer) row[0]);
            dto.setTitle((String) row[1]);
            dto.setContent((String) row[2]);
            dto.setImageUrl((String) row[3]);
            dto.setPublishedDate(((java.sql.Timestamp) row[4]).toLocalDateTime());
            dto.setCategoryName((String) row[5]);
            return dto;
        }).collect(Collectors.toList());
    }

    // Đếm số lượng tin tức theo danh mục
    public long getNewsCountByCategory(Integer categoryId) {
        return newsRepository.countNewsByCategory(categoryId);
    }

    // Thêm tin tức mới
    public NewsDTO createNews(NewsDTO newsDTO) {
        Categories category = categoriesRepository.findById(newsDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục!"));

        News news = new News();
        news.setCategory(category);
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        news.setImageUrl(newsDTO.getImageUrl());

        News savedNews = newsRepository.save(news);
        return convertToDTO(savedNews);
    }

    // Cập nhật tin tức
    public NewsDTO updateNews(Integer id, NewsDTO newsDTO) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tin tức!"));

        Categories category = categoriesRepository.findById(newsDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục!"));

        news.setCategory(category);
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        news.setImageUrl(newsDTO.getImageUrl());

        News updatedNews = newsRepository.save(news);
        return convertToDTO(updatedNews);
    }

    // Xóa tin tức
    public void deleteNews(Integer id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tin tức!"));
        newsRepository.delete(news);
    }

    // Chuyển từ Entity sang DTO
    private NewsDTO convertToDTO(News news) {
        NewsDTO dto = new NewsDTO();
        dto.setNewsId(news.getNewsId());
        dto.setCategoryId(news.getCategory().getCategoryId());
        dto.setCategoryName(news.getCategory().getCategoryName());
        dto.setTitle(news.getTitle());
        dto.setContent(news.getContent());
        dto.setImageUrl(news.getImageUrl());
        dto.setPublishedDate(news.getPublishedDate());
        return dto;
    }
}