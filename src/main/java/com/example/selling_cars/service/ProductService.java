package com.example.selling_cars.service;

import com.example.selling_cars.entity.Product;
import com.example.selling_cars.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Lấy sản phẩm theo ID
    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    // Lấy sản phẩm theo tên
    public Optional<Product> getProductByName(String productName) {
        return productRepository.findByProductName(productName);
    }

    // Tìm sản phẩm theo từ khóa
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByProductNameContainingIgnoreCase(keyword);
    }

    // Lấy sản phẩm theo danh mục
    public List<Product> getProductsByCategory(Integer categoryId) {
        return productRepository.findByCategoryCategoryId(categoryId);
    }

    // Lấy sản phẩm theo khoảng giá
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    // Lấy sản phẩm theo năm sản xuất
    public List<Product> getProductsByYear(Integer year) {
        return productRepository.findByManufactureYear(year);
    }

    // Lấy sản phẩm theo trạng thái
    public List<Product> getProductsByStatus(String status) {
        return productRepository.findByStatus(status);
    }

    // Lấy sản phẩm theo model
    public List<Product> getProductsByModel(String model) {
        return productRepository.findByModelContainingIgnoreCase(model);
    }

    // Lấy sản phẩm theo số km đã chạy
    public List<Product> getProductsByMileage(Integer maxMileage) {
        return productRepository.findByMileageLessThanEqual(maxMileage);
    }

    // Lấy sản phẩm còn trong kho
    public List<Product> getAvailableProducts(String status, Integer minQuantity) {
        List<Product> products = productRepository.findByStatus(status);
        return products.stream()
                .filter(product -> product.getQuantity() >= minQuantity)
                .collect(java.util.stream.Collectors.toList());
    }

    // Tìm sản phẩm theo nhiều tiêu chí
    public Page<Product> findProductsByFilters(Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice,
                                             Integer minYear, Integer maxYear, String status,
                                             Pageable pageable) {
        return productRepository.findByFilters(categoryId, status, minPrice, maxPrice, minYear, maxYear, pageable);
    }

    // Thêm sản phẩm mới
    @Transactional
    public Product createProduct(Product product) {
        if (productRepository.findByProductName(product.getProductName()).isPresent()) {
            throw new RuntimeException("Product with name " + product.getProductName() + " already exists");
        }
        return productRepository.save(product);
    }

    // Cập nhật sản phẩm
    @Transactional
    public Product updateProduct(Integer id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        // Kiểm tra tên mới có trùng với sản phẩm khác không
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

    // Xóa sản phẩm
    @Transactional
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    // Cập nhật số lượng sản phẩm
    @Transactional
    public Product updateProductQuantity(Integer id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setQuantity(quantity);
        return productRepository.save(product);
    }

    // Đếm số lượng sản phẩm theo trạng thái
    public long countProductsByStatus(String status) {
        return productRepository.countByStatus(status);
    }

    // Đếm số lượng sản phẩm theo danh mục
    public long countProductsByCategory(Integer categoryId) {
        return productRepository.countByCategoryCategoryId(categoryId);
    }

    public Page<Product> findByFilters(
            Integer categoryId, 
            String status, 
            BigDecimal minPrice, 
            BigDecimal maxPrice, 
            Integer minYear, 
            Integer maxYear, 
            Pageable pageable) {
        return productRepository.findByFilters(categoryId, status, minPrice, maxPrice, minYear, maxYear, pageable);
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