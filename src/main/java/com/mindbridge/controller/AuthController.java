package com.mindbridge.controller;

import com.mindbridge.dto.ApiResponseDto;
import com.mindbridge.dto.LoginRequestDto;
import com.mindbridge.dto.SignupRequestDto;
import com.mindbridge.entity.UserEntity;
import com.mindbridge.security.JwtTokenProvider;
import com.mindbridge.security.TokenService;
import com.mindbridge.service.AuthService;
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
    public ResponseEntity<ApiResponseDto<UserEntity>> signup(@Valid @RequestBody SignupRequestDto req) {
        UserEntity user = authService.signup(req);
        return ResponseEntity.ok(
                new ApiResponseDto<>(true, "회원가입이 성공적으로 완료되었습니다.", user)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<Map<String, String>>> login(@RequestBody LoginRequestDto req) {
        try {
            ApiResponseDto<Map<String, String>> response = authService.login(req);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseDto.error("로그인 실패: " + e.getMessage()));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponseDto<String>> refreshToken(
            @RequestParam Long userId,
            @RequestParam String refreshToken) {
        try {
            String newAccessToken = tokenService.refreshAccessToken(userId, refreshToken);
            return ResponseEntity.ok(ApiResponseDto.success("새로운 액세스 토큰 발급 성공", newAccessToken));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseDto.error("리프레쉬 토큰 만료 또는 유효하지 않음: " + e.getMessage()));
        }
    }
}