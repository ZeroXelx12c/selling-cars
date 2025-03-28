package com.example.selling_cars.controller;

import com.example.selling_cars.dto.ProductDTO;
import com.example.selling_cars.entity.Product;
import com.example.selling_cars.exception.ResourceNotFoundException;
import com.example.selling_cars.mapper.ProductMapper;
import com.example.selling_cars.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOs = products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) {
        Optional<Product> productOpt = productService.getProductById(id);
        Product product = productOpt.orElseThrow(() -> 
            new ResourceNotFoundException("Product", "id", id));
        return ResponseEntity.ok(productMapper.toDTO(product));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Integer categoryId) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        List<ProductDTO> productDTOs = products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProductDTO>> getProductsByStatus(@PathVariable String status) {
        List<Product> products = productService.getProductsByStatus(status);
        List<ProductDTO> productDTOs = products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String keyword) {
        List<Product> products = productService.searchProducts(keyword);
        List<ProductDTO> productDTOs = products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<ProductDTO>> filterProducts(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "productId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        Page<Product> productPage = productService.findByFilters(categoryId, status, minPrice, maxPrice, minYear, maxYear, pageable);
        
        Page<ProductDTO> productDTOPage = productPage.map(productMapper::toDTO);
        return ResponseEntity.ok(productDTOPage);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productService.createProduct(product);
        return new ResponseEntity<>(productMapper.toDTO(savedProduct), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer id, @Valid @RequestBody ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(productMapper.toDTO(updatedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ProductDTO> updateProductStatus(@PathVariable Integer id, @RequestParam String status) {
        Product product = productService.getProductById(id).orElseThrow(() ->
            new ResourceNotFoundException("Product", "id", id));
        product.setStatus(status);
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(productMapper.toDTO(updatedProduct));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countProducts() {
        long count = productService.countProducts();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> countProductsByStatus(@PathVariable String status) {
        long count = productService.countByStatus(status);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/category/{categoryId}")
    public ResponseEntity<Long> countProductsByCategory(@PathVariable Integer categoryId) {
        long count = productService.countByCategoryId(categoryId);
        return ResponseEntity.ok(count);
    }
}
