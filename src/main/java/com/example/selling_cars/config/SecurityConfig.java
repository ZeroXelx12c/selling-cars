package com.example.selling_cars.config;

import com.example.selling_cars.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsersService usersService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Tắt CSRF để dễ test API (nếu dùng frontend thì bật lại)
            .csrf().disable()
            // Cấu hình quyền truy cập
            .authorizeHttpRequests(authorize -> authorize
                // Cho phép truy cập công khai
                .requestMatchers("/api/news/**", "/api/products/**", "/api/users/register", "/api/users/login").permitAll()
                // Yêu cầu vai trò ADMIN cho các API quản lý
                .requestMatchers("/api/admin-dashboard/**", "/api/revenue-reports/**").hasRole("Admin")
                // Các request khác yêu cầu xác thực
                .anyRequest().authenticated()
            )
            // Cấu hình form login
            .formLogin(form -> form
                .loginPage("/login") // Trang đăng nhập tùy chỉnh (nếu có)
                .defaultSuccessUrl("/api/admin-dashboard/latest", true) // Chuyển hướng sau khi đăng nhập thành công
                .permitAll()
            )
            // Cấu hình logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/api/news/latest")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return phoneNumber -> {
            // Lấy thông tin người dùng từ UsersService
            return usersService.getUserByPhoneNumber(phoneNumber)
                .map(user -> org.springframework.security.core.userdetails.User
                    .withUsername(user.getPhoneNumber())
                    .password(user.getPassword()) // Mật khẩu đã mã hóa trong DB
                    .roles(user.getRole()) // Vai trò (Customer hoặc Admin)
                    .build())
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với số điện thoại: " + phoneNumber));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Mã hóa mật khẩu bằng BCrypt
    }
}