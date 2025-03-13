package com.example.selling_cars.service;

import com.example.selling_cars.dto.ProductOptionDTO;
import com.example.selling_cars.entity.ProductOptions;
import com.example.selling_cars.entity.Products;
import com.example.selling_cars.repository.ProductOptionsRepository;
import com.example.selling_cars.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductOptionsService {

    @Autowired
    private ProductOptionsRepository productOptionsRepository;

    @Autowired
    private ProductsRepository productsRepository;

    // Lấy tất cả tùy chọn
    public List<ProductOptionDTO> getAllOptions() {
        return productOptionsRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Lấy tùy chọn theo ID
    public Optional<ProductOptionDTO> getOptionById(Integer id) {
        return productOptionsRepository.findById(id).map(this::convertToDTO);
    }

    // Lấy tùy chọn theo sản phẩm
    public List<ProductOptionDTO> getOptionsByProduct(Integer productId) {
        return productOptionsRepository.findByProductProductId(productId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy tùy chọn theo sản phẩm và loại (Exterior/Interior)
    public List<ProductOptionDTO> getOptionsByProductAndType(Integer productId, String optionType) {
        return productOptionsRepository.findByProductProductIdAndOptionType(productId, optionType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Đếm số lượng tùy chọn theo sản phẩm
    public long getOptionsCountByProduct(Integer productId) {
        return productOptionsRepository.countOptionsByProductId(productId);
    }

    // Thêm tùy chọn mới
    public ProductOptionDTO createOption(ProductOptionDTO optionDTO) {
        Products product = productsRepository.findById(optionDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));

        ProductOptions option = new ProductOptions();
        option.setProduct(product);
        option.setOptionType(optionDTO.getOptionType());
        option.setOptionName(optionDTO.getOptionName());
        option.setAdditionalPrice(optionDTO.getAdditionalPrice());

        ProductOptions savedOption = productOptionsRepository.save(option);
        return convertToDTO(savedOption);
    }

    // Cập nhật tùy chọn
    public ProductOptionDTO updateOption(Integer id, ProductOptionDTO optionDTO) {
        ProductOptions option = productOptionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tùy chọn!"));

        Products product = productsRepository.findById(optionDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));

        option.setProduct(product);
        option.setOptionType(optionDTO.getOptionType());
        option.setOptionName(optionDTO.getOptionName());
        option.setAdditionalPrice(optionDTO.getAdditionalPrice());

        ProductOptions updatedOption = productOptionsRepository.save(option);
        return convertToDTO(updatedOption);
    }

    // Xóa tùy chọn
    public void deleteOption(Integer id) {
        ProductOptions option = productOptionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tùy chọn!"));
        productOptionsRepository.delete(option);
    }

    // Chuyển từ Entity sang DTO
    private ProductOptionDTO convertToDTO(ProductOptions option) {
        ProductOptionDTO dto = new ProductOptionDTO();
        dto.setOptionId(option.getOptionId());
        dto.setProductId(option.getProduct().getProductId());
        dto.setOptionType(option.getOptionType());
        dto.setOptionName(option.getOptionName());
        dto.setAdditionalPrice(option.getAdditionalPrice());
        return dto;
    }
}