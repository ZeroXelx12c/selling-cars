package com.example.selling_cars.controller;

import com.example.selling_cars.entity.Products;
import com.example.selling_cars.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductsService productsService;

    @GetMapping("/home")
    public String home(Model model) {
        // Lấy danh sách sản phẩm nổi bật từ ProductsService
        List<Products> featuredProducts = productsService.getFeaturedProducts();
        model.addAttribute("featuredProducts", featuredProducts);
        return "home"; // Trả về home.html trong templates
    }
}