package com.example.selling_cars.controller;

import com.example.selling_cars.dto.ProductOptionDTO;
import com.example.selling_cars.entity.ProductOption;
import com.example.selling_cars.exception.ResourceNotFoundException;
import com.example.selling_cars.mapper.ProductOptionMapper;
import com.example.selling_cars.service.ProductOptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products/options")
@CrossOrigin(origins = "*")
public class ProductOptionController {

    @Autowired
    private ProductOptionService productOptionService;

    @Autowired
    private ProductOptionMapper productOptionMapper;

    @GetMapping
    public ResponseEntity<List<ProductOptionDTO>> getAllOptions() {
        List<ProductOption> options = productOptionService.getAllOptions();
        List<ProductOptionDTO> optionDTOs = options.stream()
                .map(productOptionMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(optionDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOptionDTO> getOptionById(@PathVariable Integer id) {
        Optional<ProductOption> optionOpt = productOptionService.getOptionById(id);
        ProductOption option = optionOpt.orElseThrow(() ->
                new ResourceNotFoundException("ProductOption", "id", id));
        return ResponseEntity.ok(productOptionMapper.toDTO(option));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductOptionDTO>> getOptionsByProduct(@PathVariable Integer productId) {
        List<ProductOption> options = productOptionService.getOptionsByProduct(productId);
        List<ProductOptionDTO> optionDTOs = options.stream()
                .map(productOptionMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(optionDTOs);
    }

    @GetMapping("/type/{optionType}")
    public ResponseEntity<List<ProductOptionDTO>> getOptionsByType(@PathVariable String optionType) {
        List<ProductOption> options = productOptionService.getOptionsByType(optionType);
        List<ProductOptionDTO> optionDTOs = options.stream()
                .map(productOptionMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(optionDTOs);
    }

    @GetMapping("/product/{productId}/type/{optionType}")
    public ResponseEntity<List<ProductOptionDTO>> getOptionsByProductAndType(
            @PathVariable Integer productId,
            @PathVariable String optionType) {
        List<ProductOption> options = productOptionService.getOptionsByProductAndType(productId, optionType);
        List<ProductOptionDTO> optionDTOs = options.stream()
                .map(productOptionMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(optionDTOs);
    }

    @GetMapping("/product/{productId}/type/{optionType}/sorted")
    public ResponseEntity<List<ProductOptionDTO>> getOptionsByProductAndTypeSortedByPrice(
            @PathVariable Integer productId,
            @PathVariable String optionType) {
        List<ProductOption> options = productOptionService.getOptionsByProductAndTypeSortedByPrice(productId, optionType);
        List<ProductOptionDTO> optionDTOs = options.stream()
                .map(productOptionMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(optionDTOs);
    }

    @GetMapping("/name/{optionName}")
    public ResponseEntity<ProductOptionDTO> getOptionByName(@PathVariable String optionName) {
        Optional<ProductOption> optionOpt = productOptionService.getOptionByName(optionName);
        ProductOption option = optionOpt.orElseThrow(() ->
                new ResourceNotFoundException("ProductOption", "name", optionName));
        return ResponseEntity.ok(productOptionMapper.toDTO(option));
    }

    @PostMapping
    public ResponseEntity<ProductOptionDTO> createOption(@Valid @RequestBody ProductOptionDTO optionDTO) {
        ProductOption option = productOptionMapper.toEntity(optionDTO);
        ProductOption savedOption = productOptionService.createOption(option);
        return new ResponseEntity<>(productOptionMapper.toDTO(savedOption), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductOptionDTO> updateOption(
            @PathVariable Integer id,
            @Valid @RequestBody ProductOptionDTO optionDTO) {
        ProductOption option = productOptionMapper.toEntity(optionDTO);
        ProductOption updatedOption = productOptionService.updateOption(id, option);
        return ResponseEntity.ok(productOptionMapper.toDTO(updatedOption));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOption(@PathVariable Integer id) {
        productOptionService.deleteOption(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{productId}/{optionName}")
    public ResponseEntity<Boolean> existsByProductAndName(
            @PathVariable Integer productId,
            @PathVariable String optionName) {
        boolean exists = productOptionService.existsByProductAndName(productId, optionName);
        return ResponseEntity.ok(exists);
    }
} 