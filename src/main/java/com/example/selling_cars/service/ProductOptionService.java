package com.example.selling_cars.service;

import com.example.selling_cars.entity.ProductOption;
import com.example.selling_cars.repository.ProductOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductOptionService {
    @Autowired
    private ProductOptionRepository productOptionRepository;

    // Lấy tất cả tùy chọn
    public List<ProductOption> getAllOptions() {
        return productOptionRepository.findAll();
    }

    // Lấy tùy chọn theo ID
    public Optional<ProductOption> getOptionById(Integer id) {
        return productOptionRepository.findById(id);
    }

    // Lấy tùy chọn theo sản phẩm
    public List<ProductOption> getOptionsByProduct(Integer productId) {
        return productOptionRepository.findByProductProductId(productId);
    }

    // Lấy tùy chọn theo loại
    public List<ProductOption> getOptionsByType(String optionType) {
        return productOptionRepository.findByOptionType(optionType);
    }

    // Lấy tùy chọn theo sản phẩm và loại
    public List<ProductOption> getOptionsByProductAndType(Integer productId, String optionType) {
        return productOptionRepository.findByProductProductIdAndOptionType(productId, optionType);
    }

    // Lấy tùy chọn theo tên
    public Optional<ProductOption> getOptionByName(String optionName) {
        return productOptionRepository.findByOptionName(optionName);
    }

    // Lấy tùy chọn theo sản phẩm và tên
    public Optional<ProductOption> getOptionByProductAndName(Integer productId, String optionName) {
        return productOptionRepository.findByProductProductIdAndOptionName(productId, optionName);
    }

    // Lấy danh sách tùy chọn nội thất của sản phẩm
    public List<ProductOption> getInteriorOptionsByProduct(Integer productId) {
        return productOptionRepository.findByProductProductIdAndOptionTypeOrderByAdditionalPriceAsc(productId, "INTERIOR");
    }

    // Thêm tùy chọn mới
    @Transactional
    public ProductOption createOption(ProductOption option) {
        if (productOptionRepository.existsByProductProductIdAndOptionName(
                option.getProduct().getProductId(), option.getOptionName())) {
            throw new RuntimeException("Option with name " + option.getOptionName() + 
                    " already exists for product " + option.getProduct().getProductId());
        }
        return productOptionRepository.save(option);
    }

    // Cập nhật tùy chọn
    @Transactional
    public ProductOption updateOption(Integer id, ProductOption optionDetails) {
        ProductOption option = productOptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Option not found with id: " + id));

        // Kiểm tra tên mới có trùng với tùy chọn khác của cùng sản phẩm không
        if (!option.getOptionName().equals(optionDetails.getOptionName()) &&
            productOptionRepository.existsByProductProductIdAndOptionName(
                    option.getProduct().getProductId(), optionDetails.getOptionName())) {
            throw new RuntimeException("Option with name " + optionDetails.getOptionName() + 
                    " already exists for product " + option.getProduct().getProductId());
        }

        option.setOptionName(optionDetails.getOptionName());
        option.setOptionType(optionDetails.getOptionType());
        option.setDescription(optionDetails.getDescription());
        option.setAdditionalPrice(optionDetails.getAdditionalPrice());
        option.setProduct(optionDetails.getProduct());

        return productOptionRepository.save(option);
    }

    // Xóa tùy chọn
    @Transactional
    public void deleteOption(Integer id) {
        productOptionRepository.deleteById(id);
    }

    // Cập nhật giá tùy chọn
    @Transactional
    public ProductOption updateOptionPrice(Integer id, BigDecimal additionalPrice) {
        ProductOption option = productOptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Option not found with id: " + id));
        option.setAdditionalPrice(additionalPrice);
        return productOptionRepository.save(option);
    }

    // Kiểm tra tùy chọn có tồn tại
    public boolean existsOption(Integer productId, String optionName) {
        return productOptionRepository.existsByProductProductIdAndOptionName(productId, optionName);
    }

    public List<ProductOption> getOptionsByProductAndTypeSortedByPrice(Integer productId, String optionType) {
        // Return product options for a specific product and type, sorted by additional price
        return productOptionRepository.findByProductProductIdAndOptionTypeOrderByAdditionalPriceAsc(productId, optionType);
    }

    public boolean existsByProductAndName(Integer productId, String optionName) {
        return productOptionRepository.existsByProductProductIdAndOptionName(productId, optionName);
    }
} 