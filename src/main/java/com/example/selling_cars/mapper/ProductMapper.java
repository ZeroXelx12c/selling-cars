package com.example.selling_cars.mapper;

import com.example.selling_cars.dto.ProductDTO;
import com.example.selling_cars.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    
    @Autowired
    private CategoryMapper categoryMapper;
    
    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setModel(product.getModel());
        dto.setManufactureYear(product.getManufactureYear());
        dto.setEngine(product.getEngine());
        dto.setMileage(product.getMileage());
        dto.setStatus(product.getStatus());
        dto.setDescription(product.getDescription());
        dto.setCreatedAt(product.getCreatedAt());
        
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getCategoryId());
        }
        
        return dto;
    }

    public Product toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Product product = new Product();
        product.setProductId(dto.getProductId());
        product.setProductName(dto.getProductName());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());
        product.setModel(dto.getModel());
        product.setManufactureYear(dto.getManufactureYear());
        product.setEngine(dto.getEngine());
        product.setMileage(dto.getMileage());
        product.setStatus(dto.getStatus());
        product.setDescription(dto.getDescription());
        product.setCreatedAt(dto.getCreatedAt());
        
        return product;
    }
} 