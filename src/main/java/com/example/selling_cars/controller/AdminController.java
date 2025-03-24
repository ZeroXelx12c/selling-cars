package com.example.selling_cars.controller;

import com.example.selling_cars.dto.UserDTO;
import com.example.selling_cars.entity.Users;
import com.example.selling_cars.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public String showAdminPage(Model model) {
        List<Users> users = usersService.getAllUsers();
        List<UserDTO> userDTOs = (users != null && !users.isEmpty()) 
            ? users.stream().map(u -> {
                UserDTO dto = new UserDTO();
                dto.setUserId(u.getUserId());
                dto.setFullName(u.getFullName());
                dto.setPhoneNumber(u.getPhoneNumber());
                dto.setEmail(u.getEmail());
                dto.setDateOfBirth(u.getDateOfBirth());
                dto.setRole(u.getRole());
                dto.setCreatedAt(u.getCreatedAt());
                dto.setOrderCount(0);
                dto.setTotalSpending(0.0);
                return dto;
            }).collect(Collectors.toList())
            : Collections.emptyList();

        model.addAttribute("users", userDTOs);
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("activeTab", "customers");
        return "admin";
    }

    @PostMapping("/customers/add")
    public String addCustomer(@Valid @ModelAttribute("userDTO") UserDTO userDTO, 
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Users> users = usersService.getAllUsers();
            List<UserDTO> userDTOs = (users != null && !users.isEmpty()) 
                ? users.stream().map(u -> {
                    UserDTO dto = new UserDTO();
                    dto.setUserId(u.getUserId());
                    dto.setFullName(u.getFullName());
                    dto.setPhoneNumber(u.getPhoneNumber());
                    dto.setEmail(u.getEmail());
                    dto.setDateOfBirth(u.getDateOfBirth());
                    dto.setRole(u.getRole());
                    dto.setCreatedAt(u.getCreatedAt());
                    return dto;
                }).collect(Collectors.toList())
                : Collections.emptyList();
            model.addAttribute("users", userDTOs);
            model.addAttribute("activeTab", "customers");
            return "admin";
        }

        Users user = new Users();
        user.setFullName(userDTO.getFullName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmail(userDTO.getEmail());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        try {
            usersService.registerUser(user);
            return "redirect:/admin?success=Thêm khách hàng thành công!";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            List<Users> users = usersService.getAllUsers();
            List<UserDTO> userDTOs = (users != null && !users.isEmpty()) 
                ? users.stream().map(u -> {
                    UserDTO dto = new UserDTO();
                    dto.setUserId(u.getUserId());
                    dto.setFullName(u.getFullName());
                    dto.setPhoneNumber(u.getPhoneNumber());
                    dto.setEmail(u.getEmail());
                    dto.setDateOfBirth(u.getDateOfBirth());
                    dto.setRole(u.getRole());
                    dto.setCreatedAt(u.getCreatedAt());
                    return dto;
                }).collect(Collectors.toList())
                : Collections.emptyList();
            model.addAttribute("users", userDTOs);
            model.addAttribute("activeTab", "customers");
            return "admin";
        }
    }

    @PostMapping("/customers/update")
    public String updateCustomer(@Valid @ModelAttribute("userDTO") UserDTO userDTO, 
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Users> users = usersService.getAllUsers();
            List<UserDTO> userDTOs = (users != null && !users.isEmpty()) 
                ? users.stream().map(u -> {
                    UserDTO dto = new UserDTO();
                    dto.setUserId(u.getUserId());
                    dto.setFullName(u.getFullName());
                    dto.setPhoneNumber(u.getPhoneNumber());
                    dto.setEmail(u.getEmail());
                    dto.setDateOfBirth(u.getDateOfBirth());
                    dto.setRole(u.getRole());
                    dto.setCreatedAt(u.getCreatedAt());
                    return dto;
                }).collect(Collectors.toList())
                : Collections.emptyList();
            model.addAttribute("users", userDTOs);
            model.addAttribute("activeTab", "customers");
            return "admin";
        }

        Users user = new Users();
        user.setUserId(userDTO.getUserId());
        user.setFullName(userDTO.getFullName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmail(userDTO.getEmail());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        try {
            usersService.updateUser(userDTO.getUserId(), user);
            return "redirect:/admin?success=Cập nhật khách hàng thành công!";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            List<Users> users = usersService.getAllUsers();
            List<UserDTO> userDTOs = (users != null && !users.isEmpty()) 
                ? users.stream().map(u -> {
                    UserDTO dto = new UserDTO();
                    dto.setUserId(u.getUserId());
                    dto.setFullName(u.getFullName());
                    dto.setPhoneNumber(u.getPhoneNumber());
                    dto.setEmail(u.getEmail());
                    dto.setDateOfBirth(u.getDateOfBirth());
                    dto.setRole(u.getRole());
                    dto.setCreatedAt(u.getCreatedAt());
                    return dto;
                }).collect(Collectors.toList())
                : Collections.emptyList();
            model.addAttribute("users", userDTOs);
            model.addAttribute("activeTab", "customers");
            return "admin";
        }
    }

    @PostMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Integer id, Model model) {
        try {
            usersService.deleteUser(id);
            return "redirect:/admin?success=Xóa khách hàng thành công!";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            List<Users> users = usersService.getAllUsers();
            List<UserDTO> userDTOs = (users != null && !users.isEmpty()) 
                ? users.stream().map(u -> {
                    UserDTO dto = new UserDTO();
                    dto.setUserId(u.getUserId());
                    dto.setFullName(u.getFullName());
                    dto.setPhoneNumber(u.getPhoneNumber());
                    dto.setEmail(u.getEmail());
                    dto.setDateOfBirth(u.getDateOfBirth());
                    dto.setRole(u.getRole());
                    dto.setCreatedAt(u.getCreatedAt());
                    return dto;
                }).collect(Collectors.toList())
                : Collections.emptyList();
            model.addAttribute("users", userDTOs);
            model.addAttribute("activeTab", "customers");
            return "admin";
        }
    }
}