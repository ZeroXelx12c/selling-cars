package com.example.selling_cars.config;

import com.example.selling_cars.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsersService usersService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Cho phép truy cập công khai
                        .requestMatchers("/login", "/register", "/home", "/product-list", "/services", "/news",
                                "/contact", "/css/**", "/js/**")
                        .permitAll()
                        // Yêu cầu đăng nhập cho /payment
                        .requestMatchers("/payment").authenticated()
                        // Các request khác vẫn mở (nếu cần thêm trang công khai sau này)
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .rememberMe(remember -> remember
                        .rememberMeParameter("remember-me")
                        .tokenValiditySeconds(86400));
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return phoneNumber -> usersService.getUserByPhoneNumber(phoneNumber)
                .map(user -> org.springframework.security.core.userdetails.User
                        .withUsername(user.getPhoneNumber())
                        .password(user.getPassword())
                        .roles(user.getRole().toUpperCase())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Số điện thoại hoặc mật khẩu không đúng!"));
    }
}