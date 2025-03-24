package com.example.selling_cars.service;

import com.example.selling_cars.entity.Users;
import com.example.selling_cars.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    // Đăng ký người dùng (giữ nguyên)
    @Transactional
    public Users registerUser(Users user) {
        System.out.println("Đăng ký user: " + user.getPhoneNumber());
        if (usersRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new IllegalArgumentException("Số điện thoại đã được sử dụng!");
        }
        if (user.getEmail() != null && usersRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email đã được sử dụng!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("customer");
        }
        Users savedUser = usersRepository.save(user);
        System.out.println("Đã lưu user: " + savedUser.getUserId());
        return savedUser;
    }

    // Lấy người dùng theo số điện thoại (giữ nguyên)
    public Optional<Users> getUserByPhoneNumber(String phoneNumber) {
        return usersRepository.findByPhoneNumber(phoneNumber);
    }

    // Lấy người dùng theo ID (giữ nguyên)
    public Optional<Users> getUserById(Integer id) {
        return usersRepository.findById(id);
    }

    // Lấy danh sách tất cả người dùng (thêm mới)
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    // Cập nhật thông tin người dùng (thêm mới)
    @Transactional
    public Users updateUser(Integer id, Users updatedUser) {
        Optional<Users> existingUserOpt = usersRepository.findById(id);
        if (!existingUserOpt.isPresent()) {
            throw new IllegalArgumentException("Người dùng không tồn tại!");
        }

        Users existingUser = existingUserOpt.get();

        // Kiểm tra số điện thoại nếu thay đổi
        if (!existingUser.getPhoneNumber().equals(updatedUser.getPhoneNumber()) 
            && usersRepository.existsByPhoneNumber(updatedUser.getPhoneNumber())) {
            throw new IllegalArgumentException("Số điện thoại đã được sử dụng!");
        }

        // Kiểm tra email nếu thay đổi
        if (updatedUser.getEmail() != null 
            && !updatedUser.getEmail().equals(existingUser.getEmail()) 
            && usersRepository.existsByEmail(updatedUser.getEmail())) {
            throw new IllegalArgumentException("Email đã được sử dụng!");
        }

        // Cập nhật các trường
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
        existingUser.setRole(updatedUser.getRole() != null && !updatedUser.getRole().isEmpty() 
            ? updatedUser.getRole() : "customer");

        // Nếu mật khẩu được cung cấp, mã hóa và cập nhật
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return usersRepository.save(existingUser);
    }

    // Xóa người dùng (thêm mới)
    @Transactional
    public void deleteUser(Integer id) {
        if (!usersRepository.existsById(id)) {
            throw new IllegalArgumentException("Người dùng không tồn tại!");
        }
        usersRepository.deleteById(id);
    }
}