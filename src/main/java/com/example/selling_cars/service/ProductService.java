package com.example.selling_cars.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.selling_cars.entity.Product;
import com.example.selling_cars.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

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

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        if (product.getCreatedAt() == null) {
            product.setCreatedAt(LocalDateTime.now());
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Integer id, Product product) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product updatedProduct = existingProduct.get();
            updatedProduct.setCategory(product.getCategory());
            updatedProduct.setProductName(product.getProductName());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setImageUrl(product.getImageUrl());
            updatedProduct.setModel(product.getModel());
            updatedProduct.setManufactureYear(product.getManufactureYear());
            updatedProduct.setEngine(product.getEngine());
            updatedProduct.setMileage(product.getMileage());
            updatedProduct.setStatus(product.getStatus());
            updatedProduct.setDescription(product.getDescription());
            return productRepository.save(updatedProduct);
        }
        throw new IllegalArgumentException("Sản phẩm không tồn tại với ID: " + id);
    }

    public void deleteProduct(Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Sản phẩm không tồn tại với ID: " + id);
        }
    }

    // Tìm Sản Phẩm theo nhiều tiêu chí (cập nhật để hỗ trợ tìm kiếm và lọc từ giao
    // diện)
    public Page<Product> findByFilters(String keyword, String model, BigDecimal minPrice, BigDecimal maxPrice,
            Integer minYear, Integer maxYear, Pageable pageable) {
        Specification<Product> spec = Specification.where(null);

        // Tìm kiếm theo từ khóa (productName hoặc model)
        if (keyword != null && !keyword.trim().isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("productName")), "%" + keyword.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("model")), "%" + keyword.toLowerCase() + "%")));
        }

        // Lọc theo model (hãng xe)
        if (model != null && !model.equals("all")) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("model"), model));
        }

        // Lọc theo khoảng giá
        if (minPrice != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }

        // Lọc theo khoảng năm sản xuất
        if (minYear != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("manufactureYear"), minYear));
        }
        if (maxYear != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("manufactureYear"), maxYear));
        }

        return productRepository.findAll(spec, pageable);
    }

}