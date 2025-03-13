package com.example.selling_cars.controller;

import com.example.selling_cars.dto.ProductOptionDTO;
import com.example.selling_cars.service.ProductOptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-options")
public class ProductOptionsController {

    @Autowired
    private ProductOptionsService productOptionsService;

    // Lấy tất cả tùy chọn (cho admin hoặc debug)
    @GetMapping
    public ResponseEntity<List<ProductOptionDTO>> getAllOptions() {
        return ResponseEntity.ok(productOptionsService.getAllOptions());
    }

    // Lấy tùy chọn theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductOptionDTO> getOptionById(@PathVariable Integer id) {
        return productOptionsService.getOptionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lấy tùy chọn theo sản phẩm (cho giao diện người dùng)
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductOptionDTO>> getOptionsByProduct(@PathVariable Integer productId) {
        return ResponseEntity.ok(productOptionsService.getOptionsByProduct(productId));
    }

    // Lấy tùy chọn theo sản phẩm và loại (Exterior/Interior)
    @GetMapping("/product/{productId}/type/{optionType}")
    public ResponseEntity<List<ProductOptionDTO>> getOptionsByProductAndType(
            @PathVariable Integer productId,
            @PathVariable String optionType) {
        return ResponseEntity.ok(productOptionsService.getOptionsByProductAndType(productId, optionType));
    }

    // Đếm số lượng tùy chọn theo sản phẩm (cho admin)
    @GetMapping("/count/product/{productId}")
    public ResponseEntity<Long> getOptionsCountByProduct(@PathVariable Integer productId) {
        return ResponseEntity.ok(productOptionsService.getOptionsCountByProduct(productId));
    }

    // Thêm tùy chọn mới (cho admin)
    @PostMapping
    public ResponseEntity<ProductOptionDTO> createOption(@RequestBody ProductOptionDTO optionDTO) {
        return ResponseEntity.ok(productOptionsService.createOption(optionDTO));
    }

    // Cập nhật tùy chọn (cho admin)
    @PutMapping("/{id}")
    public ResponseEntity<ProductOptionDTO> updateOption(@PathVariable Integer id, @RequestBody ProductOptionDTO optionDTO) {
        return ResponseEntity.ok(productOptionsService.updateOption(id, optionDTO));
    }

    // Xóa tùy chọn (cho admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOption(@PathVariable Integer id) {
        productOptionsService.deleteOption(id);
        return ResponseEntity.noContent().build();
    }
}