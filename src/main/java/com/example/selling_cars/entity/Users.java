package com.example.selling_cars.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @NotBlank(message = "Họ và tên không được để trống!")
    @Size(max = 100, message = "Họ và tên không được vượt quá 100 ký tự!")
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @NotBlank(message = "Số điện thoại không được để trống!")
    @Size(max = 15, message = "Số điện thoại không được vượt quá 15 ký tự!")
    @Pattern(regexp = "^\\d{10,15}$", message = "Số điện thoại phải là số và từ 10-15 ký tự!")
    @Column(name = "phone_number", nullable = false, unique = true, length = 15)
    private String phoneNumber;

    @Email(message = "Email không đúng định dạng!")
    @Size(max = 100, message = "Email không được vượt quá 100 ký tự!")
    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Past(message = "Ngày sinh phải là ngày trong quá khứ!")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Mật khẩu không được để trống!")
    @Size(min = 8, max = 255, message = "Mật khẩu phải từ 8 đến 255 ký tự!")
    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "role", nullable = false, length = 20)
    private String role = "customer";

    @Column(name = "remember_me")
    private Boolean rememberMe = false;

    @Column(name = "social_provider", length = 50)
    private String socialProvider;

    @Column(name = "social_id", length = 255)
    private String socialId;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}