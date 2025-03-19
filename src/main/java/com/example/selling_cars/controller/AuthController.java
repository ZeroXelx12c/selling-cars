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
        return "login"; // Trả về login.html
    }

    // Hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new Users());
        return "register"; // Trả về register.html
    }

    // Xử lý đăng ký
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") Users user, 
                               BindingResult result, 
                               @RequestParam("confirmPassword") String confirmPassword, 
                               Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (!user.getPassword().matches(passwordPattern)) {
            model.addAttribute("error", "Mật khẩu phải có chữ hoa, chữ thường, số và ký tự đặc biệt!");
            return "register";
        }
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu xác nhận không khớp!");
            return "register";
        }
        try {
            usersService.registerUser(user);
            return "redirect:/login?success=Đăng ký thành công!";
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi đăng ký: " + e.getMessage());
            e.printStackTrace();
            return "register";
        }
    }   
}