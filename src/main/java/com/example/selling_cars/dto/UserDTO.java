package com.example.selling_cars.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Integer userId;

    @NotBlank(message = "Họ và tên không được để trống!")
    private String fullName;

    @NotBlank(message = "Số điện thoại không được để trống!")
    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại phải có 10 chữ số!")
    private String phoneNumber;

    @NotBlank(message = "Email không được để trống!")
    @Email(message = "Email không hợp lệ!")
    private String email;

    @NotNull(message = "Ngày sinh không được để trống!")
    @Past(message = "Ngày sinh phải là ngày trong quá khứ!")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Mật khẩu không được để trống!")
    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", 
             message = "Mật khẩu phải có chữ hoa, chữ thường, số và ký tự đặc biệt!")
    private String password;

    private String role = "Customer"; // Mặc định là Customer
    private String socialProvider;
    private String socialId;
    private boolean rememberMe;

    private LocalDateTime createdAt;

    // Chức năng bổ sung
    private int orderCount; // Số đơn hàng của người dùng
    private double totalSpending; // Tổng chi tiêu
}