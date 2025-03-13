package com.example.selling_cars.controller;

import com.example.selling_cars.entity.Categories;
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
    public ResponseEntity<List<Categories>> getAllCategories() {
        return ResponseEntity.ok(categoriesService.getAllCategories());
    }

    // Lấy danh mục theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Categories> getCategoryById(@PathVariable Integer id) {
        return categoriesService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tìm danh mục theo tên
    @GetMapping("/search")
    public ResponseEntity<Categories> getCategoryByName(@RequestParam String name) {
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
    public ResponseEntity<List<Categories>> getCategoriesWithInStockProducts() {
        return ResponseEntity.ok(categoriesService.getCategoriesWithInStockProducts());
    }

    // Thêm danh mục mới (cho admin)
    @PostMapping
    public ResponseEntity<Categories> createCategory(@RequestBody Categories category) {
        return ResponseEntity.ok(categoriesService.createCategory(category));
    }

    // Cập nhật danh mục (cho admin)
    @PutMapping("/{id}")
    public ResponseEntity<Categories> updateCategory(@PathVariable Integer id, @RequestBody Categories categoryDetails) {
        return ResponseEntity.ok(categoriesService.updateCategory(id, categoryDetails));
    }

    // Xóa danh mục (cho admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        categoriesService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}