package com.example.selling_cars.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.selling_cars.entity.Product;
import com.example.selling_cars.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // Lấy tất cả Sản Phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Lấy Sản Phẩm theo ID
    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    // Lấy Sản Phẩm theo tên
    public Optional<Product> getProductByName(String productName) {
        return productRepository.findByProductName(productName);
    }

    // Tìm Sản Phẩm theo từ khóa
    public Page<Product> searchProducts(String keyword, Pageable pageable) {
        return productRepository.findByProductNameContainingIgnoreCase(keyword, pageable);
    }

    // Lấy Sản Phẩm theo danh mục
    public List<Product> getProductsByCategory(Integer categoryId) {
        return productRepository.findByCategoryCategoryId(categoryId);
    }

    // Lấy Sản Phẩm theo khoảng giá
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    // Lấy Sản Phẩm theo năm sản xuất
    public List<Product> getProductsByYear(Integer year) {
        return productRepository.findByManufactureYear(year);
    }

    // Lấy Sản Phẩm theo trạng thái
    public List<Product> getProductsByStatus(String status) {
        return productRepository.findByStatus(status);
    }

    // Lấy Sản Phẩm theo model
    public List<Product> getProductsByModel(String model) {
        return productRepository.findByModelContainingIgnoreCase(model);
    }

    // Lấy Sản Phẩm theo số km đã chạy
    public List<Product> getProductsByMileage(Integer maxMileage) {
        return productRepository.findByMileageLessThanEqual(maxMileage);
    }

    // Lấy Sản Phẩm còn trong kho
    public List<Product> getAvailableProducts(String status, Integer minQuantity) {
        List<Product> products = productRepository.findByStatus(status);
        return products.stream()
                .filter(product -> product.getQuantity() >= minQuantity)
                .collect(java.util.stream.Collectors.toList());
    }

    // Tìm Sản Phẩm theo nhiều tiêu chí
    public Page<Product> findByFilters(Integer categoryId, String status, BigDecimal minPrice, BigDecimal maxPrice,
            Integer minYear, Integer maxYear, Pageable pageable) {
        return productRepository.findByFilters(categoryId, status, minPrice, maxPrice, minYear, maxYear, null,
                pageable);
    }

    // Thêm Sản Phẩm mới
    @Transactional
    public Product createProduct(Product product) {
        if (productRepository.findByProductName(product.getProductName()).isPresent()) {
            throw new RuntimeException("Product with name " + product.getProductName() + " already exists");
        }
        return productRepository.save(product);
    }

    // Cập nhật Sản Phẩm
    @Transactional
    public Product updateProduct(Integer id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        // Kiểm tra tên mới có trùng với Sản Phẩm khác không
        if (!product.getProductName().equals(productDetails.getProductName()) &&
                productRepository.findByProductName(productDetails.getProductName()).isPresent()) {
            throw new RuntimeException("Product with name " + productDetails.getProductName() + " already exists");
        }

        product.setProductName(productDetails.getProductName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        product.setManufactureYear(productDetails.getManufactureYear());
        product.setMileage(productDetails.getMileage());
        product.setModel(productDetails.getModel());
        product.setStatus(productDetails.getStatus());
        product.setCategory(productDetails.getCategory());
        product.setEngine(productDetails.getEngine());
        product.setImageUrl(productDetails.getImageUrl());

        return productRepository.save(product);
    }

    // Xóa Sản Phẩm
    @Transactional
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    // Cập nhật số lượng Sản Phẩm
    @Transactional
    public Product updateProductQuantity(Integer id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setQuantity(quantity);
        return productRepository.save(product);
    }

    // Đếm số lượng Sản Phẩm theo trạng thái
    public long countProductsByStatus(String status) {
        return productRepository.countByStatus(status);
    }

    // Đếm số lượng Sản Phẩm theo danh mục
    public long countProductsByCategory(Integer categoryId) {
        return productRepository.countByCategoryCategoryId(categoryId);
    }

    public Page<Product> findProductsByFilters(Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice,
            Integer minYear, Integer maxYear, String status, String model,
            Pageable pageable) {
        return productRepository.findByFilters(categoryId, status, minPrice, maxPrice, minYear, maxYear, model,
                pageable);
    }

    public long countProducts() {
        return productRepository.count();
    }

    public long countByStatus(String status) {
        return productRepository.countByStatus(status);
    }

    public long countByCategoryId(Integer categoryId) {
        return productRepository.countByCategoryCategoryId(categoryId);
    }

}