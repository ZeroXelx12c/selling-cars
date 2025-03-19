package com.example.selling_cars.service;

import com.example.selling_cars.entity.Users;
import com.example.selling_cars.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

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

    public Optional<Users> getUserByPhoneNumber(String phoneNumber) {
        return usersRepository.findByPhoneNumber(phoneNumber);
    }

    public Optional<Users> getUserById(Integer id) {
        return usersRepository.findById(id);
    }
}