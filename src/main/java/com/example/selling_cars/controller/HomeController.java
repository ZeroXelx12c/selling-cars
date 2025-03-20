package com.example.selling_cars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/product-list")
    public String showProductList() {
        return "product-list";
    }

    @GetMapping("/services")
    public String showServices() {
        return "services";
    }

    @GetMapping("/news")
    public String showNews() {
        return "news";
    }

    @GetMapping("/contact")
    public String showContact() {
        return "contact";
    }
}