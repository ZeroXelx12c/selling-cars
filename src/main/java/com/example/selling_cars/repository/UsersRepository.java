package com.example.selling_cars.repository;

import com.example.selling_cars.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    // Tìm người dùng theo số điện thoại
    Optional<Users> findByPhoneNumber(String phoneNumber);

    // Tìm người dùng theo email
    Optional<Users> findByEmail(String email);

    // Kiểm tra số điện thoại hoặc email đã tồn tại
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
}