package com.example.selling_cars.controller;

import com.example.selling_cars.dto.CategoryDTO;
import com.example.selling_cars.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    // Lấy tất cả danh mục (cho giao diện người dùng hoặc admin)
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoriesService.getAllCategories());
    }

    // Lấy danh mục theo ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer id) {
        return categoriesService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tìm danh mục theo tên
    @GetMapping("/search")
    public ResponseEntity<CategoryDTO> getCategoryByName(@RequestParam String name) {
        return categoriesService.getCategoryByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Đếm số lượng danh mục (cho admin)
    @GetMapping("/count")
    public ResponseEntity<Long> getCategoriesCount() {
        return ResponseEntity.ok(categoriesService.getCategoriesCount());
    }

    // Lấy danh mục có sản phẩm tồn kho (cho giao diện người dùng)
    @GetMapping("/instock")
    public ResponseEntity<List<CategoryDTO>> getCategoriesWithInStockProducts() {
        return ResponseEntity.ok(categoriesService.getCategoriesWithInStockProducts());
    }

    // Thêm danh mục mới (cho admin)
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoriesService.createCategory(categoryDTO));
    }

    // Cập nhật danh mục (cho admin)
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoriesService.updateCategory(id, categoryDTO));
    }

    // Xóa danh mục (cho admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        categoriesService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}