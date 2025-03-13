package com.example.selling_cars.controller;

import com.example.selling_cars.entity.Products;
import com.example.selling_cars.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    // Lấy tất cả sản phẩm (cho giao diện người dùng)
    @GetMapping
    public ResponseEntity<List<Products>> getAllProducts() {
        return ResponseEntity.ok(productsService.getAllProducts());
    }

    // Lấy sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable Integer id) {
        return productsService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tìm kiếm sản phẩm theo tên
    @GetMapping("/search")
    public ResponseEntity<List<Products>> searchProductsByName(@RequestParam String name) {
        return ResponseEntity.ok(productsService.searchProductsByName(name));
    }

    // Lấy sản phẩm theo danh mục
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Products>> getProductsByCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(productsService.getProductsByCategory(categoryId));
    }

    // Đếm số sản phẩm tồn kho (cho admin dashboard)
    @GetMapping("/instock-count")
    public ResponseEntity<Long> getInStockCount() {
        return ResponseEntity.ok(productsService.getInStockCount());
    }

    // Lấy sản phẩm nổi bật (cho trang chủ)
    @GetMapping("/featured")
    public ResponseEntity<List<Products>> getFeaturedProducts() {
        return ResponseEntity.ok(productsService.getFeaturedProducts());
    }

    // Thêm sản phẩm mới (cho admin)
    @PostMapping
    public ResponseEntity<Products> createProduct(@RequestBody Products product) {
        return ResponseEntity.ok(productsService.createProduct(product));
    }

    // Cập nhật sản phẩm (cho admin)
    @PutMapping("/{id}")
    public ResponseEntity<Products> updateProduct(@PathVariable Integer id, @RequestBody Products productDetails) {
        return ResponseEntity.ok(productsService.updateProduct(id, productDetails));
    }

    // Xóa sản phẩm (cho admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productsService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/from-sp")
    public ResponseEntity<List<Products>> getProductsFromStoredProcedure(@RequestParam(required = false) Integer categoryId) {
        return ResponseEntity.ok(productsService.getProductsFromStoredProcedure(categoryId));
    }
}