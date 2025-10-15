package com.example.mindbridge.controller;

import com.example.mindbridge.dto.ApiResponseDTO;
import com.example.mindbridge.dto.LoginRequestDTO;
import com.example.mindbridge.dto.SignupRequestDTO;
import com.example.mindbridge.entity.UserEntity;
import com.example.mindbridge.security.JwtTokenProvider;
import com.example.mindbridge.security.TokenService;
import com.example.mindbridge.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;

    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider, TokenService tokenService) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenService = tokenService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO<UserEntity>> signup(@Valid @RequestBody SignupRequestDTO req) {
        UserEntity user = authService.signup(req);
        return ResponseEntity.ok(
                new ApiResponseDTO<>(true, "회원가입이 성공적으로 완료되었습니다.", user)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> login(@RequestBody LoginRequestDTO req) {
        try {
            ApiResponseDTO<Map<String, String>> response = authService.login(req);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseDTO.error("로그인 실패: " + e.getMessage()));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponseDTO<String>> refreshToken(
            @RequestParam Long userId,
            @RequestParam String refreshToken) {
        try {
            String newAccessToken = tokenService.refreshAccessToken(userId, refreshToken);
            return ResponseEntity.ok(ApiResponseDTO.success("새로운 액세스 토큰 발급 성공", newAccessToken));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseDTO.error("리프레쉬 토큰 만료 또는 유효하지 않음: " + e.getMessage()));
        }
    }
}