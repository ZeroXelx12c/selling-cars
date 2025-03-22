package com.example.selling_cars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/products")
public class ProductController {
    

    @GetMapping
    public String showCarPage() {
        return "car";
    }
}
