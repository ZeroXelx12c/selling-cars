package com.example.selling_cars.controller;

import com.example.selling_cars.dto.CategoryDTO;
import com.example.selling_cars.entity.Category;
import com.example.selling_cars.exception.ResourceNotFoundException;
import com.example.selling_cars.mapper.CategoryMapper;
import com.example.selling_cars.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer id) {
        Optional<Category> categoryOpt = categoryService.getCategoryById(id);
        Category category = categoryOpt.orElseThrow(() -> 
            new ResourceNotFoundException("Category", "id", id));
        return ResponseEntity.ok(categoryMapper.toDTO(category));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByStatus(@PathVariable String status) {
        List<Category> categories = categoryService.getCategoriesByStatus(status);
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDTOs);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        Category savedCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(categoryMapper.toDTO(savedCategory), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Integer id, @Valid @RequestBody CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        Category updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(categoryMapper.toDTO(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<CategoryDTO> updateCategoryStatus(@PathVariable Integer id, @RequestParam String status) {
        Category category = categoryService.getCategoryById(id).orElseThrow(() ->
            new ResourceNotFoundException("Category", "id", id));
        category.setStatus(status);
        Category updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(categoryMapper.toDTO(updatedCategory));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countCategories() {
        long count = categoryService.countCategories();
        return ResponseEntity.ok(count);
    }
} 