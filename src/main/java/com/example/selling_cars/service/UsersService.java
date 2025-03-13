package com.example.selling_cars.service;

import com.example.selling_cars.entity.Users;
import com.example.selling_cars.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    // Lấy tất cả người dùng
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    // Lấy người dùng theo ID
    public Optional<Users> getUserById(Integer id) {
        return usersRepository.findById(id);
    }

    // Lấy người dùng theo username
    public Optional<Users> getUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    // Lấy người dùng theo email
    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    // Đếm tổng số khách hàng
    public long getTotalCustomers() {
        return usersRepository.countCustomers();
    }

    // Thêm người dùng mới (đăng ký)
    public Users createUser(Users user) {
        // Kiểm tra xem username hoặc email đã tồn tại chưa
        if (usersRepository.findByUsername(user.getUsername()).isPresent() ||
            usersRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Username hoặc email đã tồn tại!");
        }
        return usersRepository.save(user);
    }

    // Cập nhật thông tin người dùng
    public Users updateUser(Integer id, Users userDetails) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));

        user.setFullName(userDetails.getFullName());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setAddress(userDetails.getAddress());
        // Không cho phép cập nhật username, password, role trực tiếp qua API này

        return usersRepository.save(user);
    }

    // Xóa người dùng
    public void deleteUser(Integer id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));
        usersRepository.delete(user);
    }

    // Đăng nhập qua Google/Facebook
    public Users loginWithSocial(String socialLoginId, String socialLoginProvider, Users userDetails) {
        Optional<Users> existingUser = usersRepository.findBySocialLoginIdAndSocialLoginProvider(socialLoginId, socialLoginProvider);
        if (existingUser.isPresent()) {
            return existingUser.get();
        } else {
            userDetails.setSocialLoginId(socialLoginId);
            userDetails.setSocialLoginProvider(socialLoginProvider);
            return usersRepository.save(userDetails);
        }
    }

    public List<Users> getCustomersFromStoredProcedure() {
    List<Object[]> results = usersRepository.getCustomersNative();
        return results.stream().map(row -> {
            Users user = new Users();
            user.setUserId((Integer) row[0]);
            user.setUsername((String) row[1]);
            user.setFullName((String) row[2]);
            user.setEmail((String) row[3]);
            user.setPhoneNumber((String) row[4]);
            user.setAddress((String) row[5]);
            user.setCreatedAt(((Timestamp) row[6]).toLocalDateTime());
            return user;
        }).collect(Collectors.toList());
    }
}