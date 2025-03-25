package com.example.selling_cars.mapper;

import com.example.selling_cars.dto.ProductOptionDTO;
import com.example.selling_cars.entity.ProductOption;
import org.springframework.stereotype.Component;

@Component
public class ProductOptionMapper {
    
    public ProductOptionDTO toDTO(ProductOption option) {
        if (option == null) {
            return null;
        }
        
        ProductOptionDTO dto = new ProductOptionDTO();
        dto.setOptionId(option.getOptionId());
        dto.setOptionName(option.getOptionName());
        dto.setOptionType(option.getOptionType());
        dto.setDescription(option.getDescription());
        dto.setAdditionalPrice(option.getAdditionalPrice());
        
        if (option.getProduct() != null) {
            dto.setProductId(option.getProduct().getProductId());
        }
        
        return dto;
    }

    public ProductOption toEntity(ProductOptionDTO dto) {
        if (dto == null) {
            return null;
        }
        
        ProductOption option = new ProductOption();
        option.setOptionId(dto.getOptionId());
        option.setOptionName(dto.getOptionName());
        option.setOptionType(dto.getOptionType());
        option.setDescription(dto.getDescription());
        option.setAdditionalPrice(dto.getAdditionalPrice());
        
        return option;
    }
} 