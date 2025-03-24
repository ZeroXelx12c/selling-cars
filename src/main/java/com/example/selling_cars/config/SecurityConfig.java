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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsersService usersService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login", "/register", "/home", "/product-list", "/services", "/news", "/contact", "/css/**", "/js/**").permitAll()
                .requestMatchers("/payment").authenticated()
                .requestMatchers("/admin/**").hasRole("ADMIN") // Bảo vệ tất cả endpoint dưới /admin
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler())
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .rememberMe(remember -> remember
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(86400)
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/admin/customers/**") // Vô hiệu hóa CSRF cho POST trong admin
            );
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

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            if ("ROLE_ADMIN".equals(role)) {
                response.sendRedirect("/admin");
            } else {
                response.sendRedirect("/home");
            }
        };
    }
}