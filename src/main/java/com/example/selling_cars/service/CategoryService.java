package com.example.selling_cars.service;

import com.example.selling_cars.entity.Category;
import com.example.selling_cars.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    // Lấy tất cả danh mục
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Lấy danh mục theo ID
    public Optional<Category> getCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }

    // Lấy danh mục theo tên
    public Optional<Category> getCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    // Tìm danh mục theo từ khóa
    public List<Category> searchCategories(String keyword) {
        return categoryRepository.findByCategoryNameContainingIgnoreCase(keyword);
    }

    // Lấy danh sách danh mục có sản phẩm
    public List<Category> getCategoriesWithProducts() {
        return categoryRepository.findByProductsIsNotNull();
    }

    // Lấy danh sách danh mục có tin tức
    public List<Category> getCategoriesWithNews() {
        return categoryRepository.findByNewsIsNotNull();
    }

    // Thêm danh mục mới
    @Transactional
    public Category createCategory(Category category) {
        if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new RuntimeException("Category with name " + category.getCategoryName() + " already exists");
        }
        return categoryRepository.save(category);
    }

    // Cập nhật danh mục
    @Transactional
    public Category updateCategory(Integer id, Category categoryDetails) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        // Kiểm tra tên mới có trùng với danh mục khác không
        if (!category.getCategoryName().equals(categoryDetails.getCategoryName()) &&
            categoryRepository.existsByCategoryName(categoryDetails.getCategoryName())) {
            throw new RuntimeException("Category with name " + categoryDetails.getCategoryName() + " already exists");
        }

        category.setCategoryName(categoryDetails.getCategoryName());
        category.setDescription(categoryDetails.getDescription());
        category.setImageUrl(categoryDetails.getImageUrl());
        category.setStatus(categoryDetails.getStatus());

        return categoryRepository.save(category);
    }

    // Xóa danh mục
    @Transactional
    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        categoryRepository.delete(category);
    }

    // Kiểm tra danh mục có tồn tại
    public boolean existsCategory(String categoryName) {
        return categoryRepository.existsByCategoryName(categoryName);
    }

    public List<Category> getCategoriesByStatus(String status) {
        return categoryRepository.findByStatus(status);
    }

    public long countCategories() {
        return categoryRepository.count();
    }
} 