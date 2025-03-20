package com.example.selling_cars.controller;

import com.example.selling_cars.entity.Users;
import com.example.selling_cars.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") Users user, 
                               BindingResult result, 
                               @RequestParam("confirmPassword") String confirmPassword, 
                               Model model) {
        if (result.hasErrors()) {
            return "register"; // Trả về trang đăng ký nếu có lỗi validation
        }

        // Kiểm tra định dạng mật khẩu
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (!user.getPassword().matches(passwordPattern)) {
            model.addAttribute("error", "Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt!");
            return "register";
        }

        // Kiểm tra xác nhận mật khẩu
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu xác nhận không khớp!");
            return "register";
        }

        try {
            usersService.registerUser(user); // Lưu user vào database
            return "redirect:/login?success=Đăng ký thành công!"; // Chuyển hướng đến /login
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi đăng ký: " + e.getMessage());
            e.printStackTrace();
            return "register";
        }
    }
}