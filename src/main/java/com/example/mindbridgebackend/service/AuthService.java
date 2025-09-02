package com.example.mindbridgebackend.service;

import com.example.mindbridgebackend.dto.LoginRequest;
import com.example.mindbridgebackend.dto.SignupRequest;
import com.example.mindbridgebackend.entity.User;
import com.example.mindbridgebackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    public void signup(SignupRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
        // 코드 수정
    }
//    public String login(LoginRequest req) {
//        var token = new UsernamePasswordAuthenticationToken();
//        authenticationManager.authenticate(token);
//        var user = userRepository.findByUsername(req.getUsername()).orElseThrow();
//        return "user";
//    }
}
