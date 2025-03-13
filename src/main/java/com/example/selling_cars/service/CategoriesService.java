package com.example.selling_cars.service;

import com.example.selling_cars.entity.Categories;
import com.example.selling_cars.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    // Lấy tất cả danh mục
    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }

    // Lấy danh mục theo ID
    public Optional<Categories> getCategoryById(Integer id) {
        return categoriesRepository.findById(id);
    }

    // Tìm danh mục theo tên
    public Optional<Categories> getCategoryByName(String name) {
        return categoriesRepository.findByCategoryNameIgnoreCase(name);
    }

    // Đếm số lượng danh mục
    public long getCategoriesCount() {
        return categoriesRepository.countCategories();
    }

    // Lấy danh mục có sản phẩm tồn kho
    public List<Categories> getCategoriesWithInStockProducts() {
        return categoriesRepository.findCategoriesWithInStockProducts();
    }

    // Thêm danh mục mới
    public Categories createCategory(Categories category) {
        if (categoriesRepository.findByCategoryNameIgnoreCase(category.getCategoryName()).isPresent()) {
            throw new RuntimeException("Danh mục đã tồn tại!");
        }
        return categoriesRepository.save(category);
    }

    // Cập nhật danh mục
    public Categories updateCategory(Integer id, Categories categoryDetails) {
        Categories category = categoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục!"));

        category.setCategoryName(categoryDetails.getCategoryName());
        category.setDescription(categoryDetails.getDescription());

        return categoriesRepository.save(category);
    }

    // Xóa danh mục
    public void deleteCategory(Integer id) {
        Categories category = categoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục!"));
        
        // Kiểm tra xem danh mục có sản phẩm không trước khi xóa
        if (!category.getProducts().isEmpty()) {
            throw new RuntimeException("Không thể xóa danh mục vì vẫn còn sản phẩm thuộc danh mục này!");
        }
        categoriesRepository.delete(category);
    }
}