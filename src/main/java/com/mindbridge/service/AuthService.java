package com.mindbridge.service;

import com.mindbridge.dto.ApiResponseDto;
import com.mindbridge.dto.LoginRequestDto;
import com.mindbridge.entity.UserEntity;
import com.mindbridge.repository.UserRepository;
import com.mindbridge.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public ApiResponseDto<String> login(LoginRequestDto req) {
        UserEntity user = userRepository.findByUserid(req.getUserid())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return new ApiResponseDto<>(false, "비밀번호가 일치하지 않습니다.", null);
        }

        String token = jwtTokenProvider.createToken(user.getUserid());

        return new ApiResponseDto<>(true, "로그인에 성공하였습니다.", token);
        //return jwtUtil.generateToken(user.getUserid());
    }
}
