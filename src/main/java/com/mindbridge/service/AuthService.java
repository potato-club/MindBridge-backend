package com.mindbridge.service;

import com.mindbridge.dto.ApiResponseDto;
import com.mindbridge.dto.LoginRequestDto;
import com.mindbridge.dto.SignupRequestDto;
import com.mindbridge.entity.UserEntity;
import com.mindbridge.repository.UserRepository;
import com.mindbridge.security.JwtTokenProvider;
import com.mindbridge.security.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenService = tokenService;
    }

    public UserEntity signup(SignupRequestDto req) {
        // 아이디 중복 확인
        if (userRepository.existsByLoginId(req.getLoginId())) {
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
                .loginId(req.getLoginId())
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
    public ApiResponseDto<Map<String, String>> login(LoginRequestDto req) {
        UserEntity user = userRepository.findByLoginId(req.getLoginId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return ApiResponseDto.error("비밀번호가 일치하지 않습니다.");
        }

        long validityInMilliseconds = 3600000;
        String token = jwtTokenProvider.createToken(user.getId(), validityInMilliseconds);

        Map<String, String> tokens = tokenService.generateToken(user.getId());

        return new ApiResponseDto<>(true, "로그인에 성공하였습니다.", tokens);
    }
}
