package com.example.selling_cars.controller;

import com.example.selling_cars.dto.UserDTO;
import com.example.selling_cars.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    // Lấy tất cả người dùng (cho admin)
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(usersService.getAllUsers());
    }

    // Lấy người dùng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        return usersService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lấy người dùng theo số điện thoại
    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<UserDTO> getUserByPhoneNumber(@PathVariable String phoneNumber) {
        return usersService.getUserByPhoneNumber(phoneNumber)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Đăng ký người dùng
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(usersService.registerUser(userDTO));
    }

    // Đăng nhập bằng số điện thoại và mật khẩu
    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(
            @RequestParam String phoneNumber,
            @RequestParam String password) {
        return ResponseEntity.ok(usersService.loginUser(phoneNumber, password));
    }

    // Đăng nhập qua mạng xã hội
    @PostMapping("/social-login")
    public ResponseEntity<UserDTO> loginSocialUser(
            @RequestParam String socialProvider,
            @RequestParam String socialId,
            @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(usersService.loginSocialUser(socialProvider, socialId, userDTO));
    }

    // Cập nhật thông tin người dùng
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(usersService.updateUser(id, userDTO));
    }

    // Xóa người dùng (cho admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Đếm tổng số khách hàng (cho admin dashboard)
    @GetMapping("/total-customers")
    public ResponseEntity<Long> getTotalCustomers() {
        return ResponseEntity.ok(usersService.getTotalCustomers());
    }
}