package com.example.selling_cars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Trả về file login.html trong templates
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register"; // Tạo file register.html sau nếu cần
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password"; // Tạo file forgot-password.html sau nếu cần
    }
}