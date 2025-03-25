package com.example.selling_cars.mapper;

import com.example.selling_cars.dto.NewsDTO;
import com.example.selling_cars.entity.News;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper {
    
    public NewsDTO toDTO(News news) {
        if (news == null) {
            return null;
        }
        
        NewsDTO dto = new NewsDTO();
        dto.setNewsId(news.getNewsId());
        dto.setTitle(news.getTitle());
        dto.setContent(news.getContent());
        dto.setSummary(news.getSummary());
        dto.setImageUrl(news.getImageUrl());
        dto.setPublishedDate(news.getPublishedDate());
        dto.setStatus(news.getStatus());
        
        if (news.getCategory() != null) {
            dto.setCategoryId(news.getCategory().getCategoryId());
        }
        
        return dto;
    }

    public News toEntity(NewsDTO dto) {
        if (dto == null) {
            return null;
        }
        
        News news = new News();
        news.setNewsId(dto.getNewsId());
        news.setTitle(dto.getTitle());
        news.setContent(dto.getContent());
        news.setSummary(dto.getSummary());
        news.setImageUrl(dto.getImageUrl());
        news.setPublishedDate(dto.getPublishedDate());
        news.setStatus(dto.getStatus());
        
        return news;
    }
} 