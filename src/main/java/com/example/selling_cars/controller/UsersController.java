package com.example.selling_cars.controller;

import com.example.selling_cars.entity.Users;
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

    // Lấy tất cả người dùng (dành cho admin)
    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(usersService.getAllUsers());
    }

    // Lấy người dùng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Integer id) {
        return usersService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lấy người dùng theo username
    @GetMapping("/username/{username}")
    public ResponseEntity<Users> getUserByUsername(@PathVariable String username) {
        return usersService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Đếm tổng số khách hàng (dành cho dashboard admin)
    @GetMapping("/total-customers")
    public ResponseEntity<Long> getTotalCustomers() {
        return ResponseEntity.ok(usersService.getTotalCustomers());
    }

    // Đăng ký người dùng mới
    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody Users user) {
        return ResponseEntity.ok(usersService.createUser(user));
    }

    // Cập nhật thông tin người dùng
    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Integer id, @RequestBody Users userDetails) {
        return ResponseEntity.ok(usersService.updateUser(id, userDetails));
    }

    // Xóa người dùng (dành cho admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Đăng nhập qua Google/Facebook
    @PostMapping("/social-login")
    public ResponseEntity<Users> socialLogin(
            @RequestParam String socialLoginId,
            @RequestParam String socialLoginProvider,
            @RequestBody Users userDetails) {
        return ResponseEntity.ok(usersService.loginWithSocial(socialLoginId, socialLoginProvider, userDetails));
    }

    @GetMapping("/customers-from-sp")
    public ResponseEntity<List<Users>> getCustomersFromStoredProcedure() {
        return ResponseEntity.ok(usersService.getCustomersFromStoredProcedure());
    }
}