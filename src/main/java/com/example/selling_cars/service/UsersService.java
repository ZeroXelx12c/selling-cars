package com.example.selling_cars.service;

import com.example.selling_cars.dto.UserDTO;
import com.example.selling_cars.entity.Users;
import com.example.selling_cars.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    // Lấy tất cả người dùng
    public List<UserDTO> getAllUsers() {
        return usersRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Lấy người dùng theo ID
    public Optional<UserDTO> getUserById(Integer id) {
        return usersRepository.findById(id).map(this::convertToDTO);
    }

    // Lấy người dùng theo số điện thoại
    public Optional<UserDTO> getUserByPhoneNumber(String phoneNumber) {
        return usersRepository.findByPhoneNumber(phoneNumber).map(this::convertToDTO);
    }

    // Đăng ký người dùng
    public UserDTO registerUser(UserDTO userDTO) {
        if (usersRepository.findByPhoneNumber(userDTO.getPhoneNumber()).isPresent() ||
            (userDTO.getEmail() != null && usersRepository.findByEmail(userDTO.getEmail()).isPresent())) {
            throw new RuntimeException("Số điện thoại hoặc email đã tồn tại!");
        }

        usersRepository.registerUser(
                userDTO.getFullName(),
                userDTO.getPhoneNumber(),
                userDTO.getEmail(),
                userDTO.getDateOfBirth(),
                userDTO.getPassword(),
                userDTO.getSocialProvider(),
                userDTO.getSocialId()
        );

        return usersRepository.findByPhoneNumber(userDTO.getPhoneNumber())
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Đăng ký thất bại!"));
    }

    // Đăng nhập bằng số điện thoại và mật khẩu
    public UserDTO loginUser(String phoneNumber, String password) {
        Optional<Users> user = usersRepository.loginUser(phoneNumber, password);
        return user.map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Số điện thoại hoặc mật khẩu không đúng!"));
    }

    // Đăng nhập qua mạng xã hội
    public UserDTO loginSocialUser(String socialProvider, String socialId, UserDTO userDTO) {
        Optional<Users> existingUser = usersRepository.loginSocialUser(socialProvider, socialId);
        if (existingUser.isPresent()) {
            return convertToDTO(existingUser.get());
        } else {
            return registerUser(userDTO); // Đăng ký nếu chưa tồn tại
        }
    }

    // Cập nhật thông tin người dùng
    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));

        user.setFullName(userDTO.getFullName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmail(userDTO.getEmail());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setRememberMe(userDTO.isRememberMe());

        Users updatedUser = usersRepository.save(user);
        return convertToDTO(updatedUser);
    }

    // Xóa người dùng
    public void deleteUser(Integer id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));
        usersRepository.delete(user);
    }

    // Đếm tổng số khách hàng
    public long getTotalCustomers() {
        return usersRepository.countCustomers();
    }

    // Chuyển từ Entity sang DTO
    private UserDTO convertToDTO(Users user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setFullName(user.getFullName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getEmail());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setRole(user.getRole());
        dto.setRememberMe(user.getRememberMe());
        dto.setSocialProvider(user.getSocialProvider());
        dto.setSocialId(user.getSocialId());
        dto.setCreatedAt(user.getCreatedAt());
        // Thêm các trường bổ sung nếu cần (orderCount, totalSpending) bằng cách truy vấn từ Orders/Payments
        return dto;
    }
}