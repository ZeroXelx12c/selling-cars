package com.example.selling_cars.controller;

import com.example.selling_cars.entity.Product;
import com.example.selling_cars.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class ProductWebController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product-list")
    public String getProductList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(defaultValue = "all") String brand,
            @RequestParam(defaultValue = "all") String year,
            @RequestParam(defaultValue = "all") String price,
            @RequestParam(defaultValue = "latest") String sort,
            @RequestParam(required = false) String keyword,
            Model model) {

        // Xử lý sắp xếp
        Sort sortOption;
        switch (sort) {
            case "priceAsc":
                sortOption = Sort.by("price").ascending();
                break;
            case "priceDesc":
                sortOption = Sort.by("price").descending();
                break;
            default:
                sortOption = Sort.by("createdAt").descending();
        }
        PageRequest pageRequest = PageRequest.of(page, size, sortOption);

        // Xử lý bộ lọc
        Integer minYear = null, maxYear = null;
        BigDecimal minPrice = null, maxPrice = null;
        String status = "in_stock"; // Chỉ hiển thị sản phẩm còn hàng

        if (!year.equals("all")) {
            if (year.equals("older")) {
                maxYear = 2020;
            } else {
                minYear = maxYear = Integer.parseInt(year);
            }
        }

        if (!price.equals("all")) {
            switch (price) {
                case "under5":
                    maxPrice = new BigDecimal("5000000000");
                    break;
                case "5to10":
                    minPrice = new BigDecimal("5000000000");
                    maxPrice = new BigDecimal("10000000000");
                    break;
                case "over10":
                    minPrice = new BigDecimal("10000000000");
                    break;
            }
        }

        // Lấy dữ liệu từ service
        Page<Product> productPage;
        if (keyword != null && !keyword.trim().isEmpty()) {
            productPage = productService.searchProducts(keyword, pageRequest);
        } else {
            productPage = productService.findProductsByFilters(null, minPrice, maxPrice, minYear, maxYear, status,
                    !brand.equals("all") ? brand : null, pageRequest);
        }

        // Truyền dữ liệu sang view
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("param", new java.util.HashMap<String, String>() {
            {
                put("brand", brand);
                put("year", year);
                put("price", price);
                put("sort", sort);
            }
        });

        return "product-list";
    }
}