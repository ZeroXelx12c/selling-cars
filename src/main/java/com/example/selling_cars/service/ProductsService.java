package com.example.selling_cars.service;

import com.example.selling_cars.entity.Categories;
import com.example.selling_cars.entity.Products;
import com.example.selling_cars.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    // Lấy tất cả sản phẩm
    public List<Products> getAllProducts() {
        return productsRepository.findAll();
    }

    // Lấy sản phẩm theo ID
    public Optional<Products> getProductById(Integer id) {
        return productsRepository.findById(id);
    }

    // Tìm sản phẩm theo tên
    public List<Products> searchProductsByName(String name) {
        return productsRepository.findByProductNameContainingIgnoreCase(name);
    }

    // Tìm sản phẩm theo danh mục
    public List<Products> getProductsByCategory(Integer categoryId) {
        return productsRepository.findByCategoryCategoryId(categoryId);
    }

    // Đếm số sản phẩm tồn kho
    public long getInStockCount() {
        return productsRepository.countInStockProducts();
    }

    // Lấy sản phẩm nổi bật
    public List<Products> getFeaturedProducts() {
        return productsRepository.findFeaturedProducts();
    }

    // Thêm sản phẩm mới
    public Products createProduct(Products product) {
        return productsRepository.save(product);
    }

    // Cập nhật sản phẩm
    public Products updateProduct(Integer id, Products productDetails) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));

        product.setCategory(productDetails.getCategory());
        product.setProductName(productDetails.getProductName());
        product.setPrice(productDetails.getPrice());
        product.setImageUrl(productDetails.getImageUrl());
        product.setModel(productDetails.getModel());
        product.setManufactureYear(productDetails.getManufactureYear());
        product.setColor(productDetails.getColor());
        product.setStatus(productDetails.getStatus());
        product.setDescription(productDetails.getDescription());

        return productsRepository.save(product);
    }

    // Xóa sản phẩm
    public void deleteProduct(Integer id) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));
        productsRepository.delete(product);
    }

    public List<Products> getProductsFromStoredProcedure(Integer categoryId) {
        List<Object[]> results = productsRepository.getProductsNative(categoryId);
        return results.stream().map(row -> {
            Products product = new Products();
            product.setProductId((Integer) row[0]);
            product.setProductName((String) row[1]);
            product.setPrice(((BigDecimal) row[2]).doubleValue());
            product.setImageUrl((String) row[3]);
            Categories category = new Categories();
            category.setCategoryName((String) row[4]);
            product.setCategory(category);
            product.setModel((String) row[5]);
            product.setManufactureYear((Integer) row[6]);
            product.setColor((String) row[7]);
            product.setStatus((String) row[8]);
            product.setDescription((String) row[9]);
            return product;
            }).collect(Collectors.toList());
        }
}