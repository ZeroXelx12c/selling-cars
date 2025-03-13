package com.example.selling_cars.service;

import com.example.selling_cars.dto.CategoryDTO;
import com.example.selling_cars.entity.Categories;
import com.example.selling_cars.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    // Lấy tất cả danh mục
    public List<CategoryDTO> getAllCategories() {
        return categoriesRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Lấy danh mục theo ID
    public Optional<CategoryDTO> getCategoryById(Integer id) {
        return categoriesRepository.findById(id).map(this::convertToDTO);
    }

    // Tìm danh mục theo tên
    public Optional<CategoryDTO> getCategoryByName(String name) {
        return categoriesRepository.findByCategoryNameIgnoreCase(name).map(this::convertToDTO);
    }

    // Đếm số lượng danh mục
    public long getCategoriesCount() {
        return categoriesRepository.countCategories();
    }

    // Lấy danh mục có sản phẩm tồn kho
    public List<CategoryDTO> getCategoriesWithInStockProducts() {
        return categoriesRepository.findCategoriesWithInStockProducts().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Thêm danh mục mới
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if (categoriesRepository.findByCategoryNameIgnoreCase(categoryDTO.getCategoryName()).isPresent()) {
            throw new RuntimeException("Danh mục đã tồn tại!");
        }

        Categories category = new Categories();
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setDescription(categoryDTO.getDescription());

        Categories savedCategory = categoriesRepository.save(category);
        return convertToDTO(savedCategory);
    }

    // Cập nhật danh mục
    public CategoryDTO updateCategory(Integer id, CategoryDTO categoryDTO) {
        Categories category = categoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục!"));

        category.setCategoryName(categoryDTO.getCategoryName());
        category.setDescription(categoryDTO.getDescription());

        Categories updatedCategory = categoriesRepository.save(category);
        return convertToDTO(updatedCategory);
    }

    // Xóa danh mục
    public void deleteCategory(Integer id) {
        Categories category = categoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục!"));

        if (!category.getProducts().isEmpty()) {
            throw new RuntimeException("Không thể xóa danh mục vì vẫn còn sản phẩm thuộc danh mục này!");
        }
        categoriesRepository.delete(category);
    }

    // Chuyển từ Entity sang DTO
    private CategoryDTO convertToDTO(Categories category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        dto.setDescription(category.getDescription());
        // Có thể thêm thông tin bổ sung như số lượng sản phẩm nếu cần
        return dto;
    }
}