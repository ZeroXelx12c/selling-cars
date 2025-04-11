package com.example.selling_cars.controller;

import com.example.selling_cars.dto.UserDTO;
import com.example.selling_cars.entity.Order;
import com.example.selling_cars.entity.Product;
import com.example.selling_cars.entity.ProductOption;
import com.example.selling_cars.entity.Users;
import com.example.selling_cars.service.OrderService;
import com.example.selling_cars.service.ProductService;
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

import com.example.selling_cars.service.ProductOptionService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductOptionService productOptionService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String showAdminPage(Model model, @RequestParam(value = "tab", defaultValue = "overview") String tab) {
        // Lấy danh sách Users và chuyển thành UserDTO
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
                    dto.setOrderCount(0); // Có thể thay bằng logic tính số đơn hàng thực tế
                    dto.setTotalSpending(0.0); // Có thể thay bằng logic tính tổng chi tiêu
                    return dto;
                }).collect(Collectors.toList())
                : Collections.emptyList();

        // Thêm dữ liệu Users vào model
        model.addAttribute("users", userDTOs);
        model.addAttribute("userDTO", new UserDTO());

        // Lấy danh sách Products
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products != null ? products : Collections.emptyList());
        model.addAttribute("product", new Product());
        model.addAttribute("option", new ProductOption());

        // Lấy danh sách Orders
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders != null ? orders : Collections.emptyList());
        model.addAttribute("order", new Order());

        // Đặt activeTab
        model.addAttribute("activeTab", tab);

        return "admin";
    }

    @PostMapping("/orders/save")
    public String saveOrder(@ModelAttribute Order order, Model model) {
        try {
            orderService.saveOrder(order);
            return "redirect:/admin?tab=orders&success=Lưu đơn hàng thành công!";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi khi lưu đơn hàng: " + e.getMessage());
            return showAdminPage(model, "orders");
        }
    }

    @PostMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") Integer id, Model model) {
        try {
            orderService.deleteOrder(id);
            return "redirect:/admin?tab=orders&success=Xóa đơn hàng thành công!";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi khi xóa đơn hàng: " + e.getMessage());
            return showAdminPage(model, "orders");
        }
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
            return "redirect:/admin";
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
            return "redirect:/admin";
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
            return "redirect:/admin";
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

    @PostMapping("/products/add")
    public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products != null ? products : Collections.emptyList());
            model.addAttribute("activeTab", "products");
            return "admin";
        }

        try {
            productService.saveProduct(product);
            return "redirect:/admin";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products != null ? products : Collections.emptyList());
            model.addAttribute("activeTab", "products");
            return "admin";
        }
    }

    @PostMapping("/products/update")
    public String updateProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products != null ? products : Collections.emptyList());
            model.addAttribute("product", product); // Giữ dữ liệu form
            model.addAttribute("activeTab", "products");
            return "admin";
        }

        try {
            productService.updateProduct(product.getProductId(), product);
            return "redirect:/admin";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products != null ? products : Collections.emptyList());
            model.addAttribute("product", product); // Giữ dữ liệu form
            model.addAttribute("activeTab", "products");
            return "admin";
        }
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id, Model model) {
        try {
            productService.deleteProduct(id);
            return "redirect:/admin";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products != null ? products : Collections.emptyList());
            model.addAttribute("activeTab", "products");
            return "admin";
        }
    }
}