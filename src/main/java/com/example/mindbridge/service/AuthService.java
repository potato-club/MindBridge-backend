package com.example.mindbridge.service;

import com.example.mindbridge.dto.ApiResponseDTO;
import com.example.mindbridge.dto.SignupRequestDTO;
import com.example.mindbridge.entity.UserEntity;
import com.example.mindbridge.repository.UserRepository;
import com.example.mindbridge.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public UserEntity signup(SignupRequestDTO req) {
        // 아이디 중복 확인
        if (userRepository.existsByUserId(req.getUserid())) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        // 닉네임 중복 확인
        if (userRepository.existsByNickname(req.getNickname())) {
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        }

        // 전화번호 중복 확인
        if (userRepository.existsByPhoneNumber(req.getPhoneNumber())) {
            throw new RuntimeException("이미 등록된 전화번호입니다.");
        }

        // 비밀번호 확인
        if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 유저 저장
        UserEntity user = UserEntity.builder()
                .userid(req.getUserid())
                .username(req.getUsername())
                .nickname(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .phoneNumber(req.getPhoneNumber())
                .gender(req.getGender())
                .birthDate(req.getBirthDate())
                .verified(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }
}
