package com.example.selling_cars.repository;

import com.example.selling_cars.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    // Tìm người dùng theo số điện thoại
    Optional<Users> findByPhoneNumber(String phoneNumber);

    // Tìm người dùng theo email
    Optional<Users> findByEmail(String email);

    // Đăng nhập bằng số điện thoại và mật khẩu
    Optional<Users> findByPhoneNumberAndPassword(String phoneNumber, String password);

    // Đăng nhập qua mạng xã hội
    Optional<Users> findBySocialProviderAndSocialId(String socialProvider, String socialId);

    // Đếm tổng số khách hàng
    @Query("SELECT COUNT(u) FROM Users u WHERE u.role = 'Customer'")
    long countCustomers();

    // Gọi stored procedure RegisterUser
    @Query(value = "EXEC RegisterUser @FullName = :fullName, @PhoneNumber = :phoneNumber, @Email = :email, " +
            "@DateOfBirth = :dateOfBirth, @Password = :password, @SocialProvider = :socialProvider, @SocialID = :socialId",
            nativeQuery = true)
    void registerUser(
            @Param("fullName") String fullName,
            @Param("phoneNumber") String phoneNumber,
            @Param("email") String email,
            @Param("dateOfBirth") LocalDate dateOfBirth,
            @Param("password") String password,
            @Param("socialProvider") String socialProvider,
            @Param("socialId") String socialId
    );

    // Gọi stored procedure LoginUser
    @Query(value = "EXEC LoginUser @PhoneNumber = :phoneNumber, @Password = :password", nativeQuery = true)
    Optional<Users> loginUser(@Param("phoneNumber") String phoneNumber, @Param("password") String password);

    // Gọi stored procedure LoginSocialUser
    @Query(value = "EXEC LoginSocialUser @SocialProvider = :socialProvider, @SocialID = :socialId", nativeQuery = true)
    Optional<Users> loginSocialUser(@Param("socialProvider") String socialProvider, @Param("socialId") String socialId);
}