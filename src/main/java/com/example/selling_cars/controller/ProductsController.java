package com.example.selling_cars.controller;

import com.example.selling_cars.dto.ProductDTO;
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
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productsService.getAllProducts());
    }

    // Lấy sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) {
        return productsService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tìm kiếm sản phẩm theo tên
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProductsByName(@RequestParam String name) {
        return ResponseEntity.ok(productsService.searchProductsByName(name));
    }

    // Lấy sản phẩm theo danh mục
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(productsService.getProductsByCategory(categoryId));
    }

    // Đếm số sản phẩm tồn kho (cho admin)
    @GetMapping("/instock-count")
    public ResponseEntity<Long> getInStockCount() {
        return ResponseEntity.ok(productsService.getInStockCount());
    }

    // Lấy sản phẩm nổi bật (cho trang chủ)
    @GetMapping("/featured")
    public ResponseEntity<List<ProductDTO>> getFeaturedProducts() {
        return ResponseEntity.ok(productsService.getFeaturedProducts());
    }

    // Lấy chi tiết sản phẩm
    @GetMapping("/details/{id}")
    public ResponseEntity<ProductDTO> getProductDetails(@PathVariable Integer id) {
        return ResponseEntity.ok(productsService.getProductDetails(id));
    }

    // Thêm sản phẩm mới (cho admin)
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productsService.createProduct(productDTO));
    }

    // Cập nhật sản phẩm (cho admin)
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productsService.updateProduct(id, productDTO));
    }

    // Xóa sản phẩm (cho admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productsService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}