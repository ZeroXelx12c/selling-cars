package com.example.selling_cars.service;

import com.example.selling_cars.dto.ProductDTO;
import com.example.selling_cars.dto.ProductOptionDTO;
import com.example.selling_cars.entity.Categories;
import com.example.selling_cars.entity.Products;
import com.example.selling_cars.repository.CategoriesRepository;
import com.example.selling_cars.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    // Lấy tất cả sản phẩm
    public List<ProductDTO> getAllProducts() {
        return productsRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Lấy sản phẩm theo ID
    public Optional<ProductDTO> getProductById(Integer id) {
        return productsRepository.findById(id).map(this::convertToDTO);
    }

    // Tìm sản phẩm theo tên
    public List<ProductDTO> searchProductsByName(String name) {
        return productsRepository.findByProductNameContainingIgnoreCase(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Tìm sản phẩm theo danh mục
    public List<ProductDTO> getProductsByCategory(Integer categoryId) {
        return productsRepository.findByCategoryCategoryId(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Đếm số sản phẩm tồn kho
    public long getInStockCount() {
        return productsRepository.countInStockProducts();
    }

    // Lấy sản phẩm nổi bật
    public List<ProductDTO> getFeaturedProducts() {
        List<Object[]> results = productsRepository.getFeaturedProductsNative();
        return results.stream().map(row -> {
            ProductDTO dto = new ProductDTO();
            dto.setProductId((Integer) row[0]);
            dto.setProductName((String) row[1]);
            dto.setPrice(((java.math.BigDecimal) row[2]).doubleValue());
            dto.setImageUrl((String) row[3]);
            dto.setManufactureYear((Integer) row[4]);
            dto.setCategoryName((String) row[5]);
            dto.setFeatured(true);
            return dto;
        }).collect(Collectors.toList());
    }

    // Lấy chi tiết sản phẩm từ stored procedure
    public ProductDTO getProductDetails(Integer productId) {
        Optional<Object[]> result = productsRepository.getProductDetailsNative(productId);
        return result.map(row -> {
            ProductDTO dto = new ProductDTO();
            dto.setProductId((Integer) row[0]);
            dto.setProductName((String) row[1]);
            dto.setPrice(((java.math.BigDecimal) row[2]).doubleValue());
            dto.setImageUrl((String) row[3]);
            dto.setModel((String) row[4]);
            dto.setManufactureYear((Integer) row[5]);
            dto.setEngine((String) row[6]);
            dto.setMileage((Integer) row[7]);
            dto.setDescription((String) row[8]);
            dto.setCategoryName((String) row[9]);

            // Parse JSON options
            String optionsJson = (String) row[10];
            // Giả sử bạn dùng Jackson để parse JSON thành List<ProductOptionDTO>
            // Ở đây mình giả lập dữ liệu options
            dto.setOptions(List.of(new ProductOptionDTO())); // Thay bằng logic parse JSON thực tế
            return dto;
        }).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));
    }

    // Thêm sản phẩm mới
    public ProductDTO createProduct(ProductDTO productDTO) {
        Categories category = categoriesRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục!"));

        Products product = new Products();
        product.setCategory(category);
        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());
        product.setModel(productDTO.getModel());
        product.setManufactureYear(productDTO.getManufactureYear());
        product.setEngine(productDTO.getEngine());
        product.setMileage(productDTO.getMileage());
        product.setStatus(productDTO.getStatus());
        product.setDescription(productDTO.getDescription());

        Products savedProduct = productsRepository.save(product);
        return convertToDTO(savedProduct);
    }

    // Cập nhật sản phẩm
    public ProductDTO updateProduct(Integer id, ProductDTO productDTO) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));

        Categories category = categoriesRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục!"));

        product.setCategory(category);
        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());
        product.setModel(productDTO.getModel());
        product.setManufactureYear(productDTO.getManufactureYear());
        product.setEngine(productDTO.getEngine());
        product.setMileage(productDTO.getMileage());
        product.setStatus(productDTO.getStatus());
        product.setDescription(productDTO.getDescription());

        Products updatedProduct = productsRepository.save(product);
        return convertToDTO(updatedProduct);
    }

    // Xóa sản phẩm
    public void deleteProduct(Integer id) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));
        productsRepository.delete(product);
    }

    // Chuyển từ Entity sang DTO
    private ProductDTO convertToDTO(Products product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setCategoryId(product.getCategory().getCategoryId());
        dto.setCategoryName(product.getCategory().getCategoryName());
        dto.setProductName(product.getProductName());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setModel(product.getModel());
        dto.setManufactureYear(product.getManufactureYear());
        dto.setEngine(product.getEngine());
        dto.setMileage(product.getMileage());
        dto.setStatus(product.getStatus());
        dto.setDescription(product.getDescription());
        dto.setCreatedAt(product.getCreatedAt());
        return dto;
    }
}