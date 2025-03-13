package com.example.selling_cars.repository;

import com.example.selling_cars.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    // Tìm người dùng theo username
    Optional<Users> findByUsername(String username);

    // Tìm người dùng theo email
    Optional<Users> findByEmail(String email);

    // Đếm tổng số khách hàng
    @Query("SELECT COUNT(u) FROM Users u WHERE u.role = 'Customer'")
    long countCustomers();

    // Tìm người dùng theo socialLoginId và socialLoginProvider
    Optional<Users> findBySocialLoginIdAndSocialLoginProvider(String socialLoginId, String socialLoginProvider);

    @Query(value = "EXEC GetCustomers", nativeQuery = true)
    List<Object[]> getCustomersNative();
}