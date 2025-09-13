package com.example.mindbridge.controller;

import com.example.mindbridge.dto.ApiResponseDTO;
import com.example.mindbridge.dto.LoginRequestDTO;
import com.example.mindbridge.dto.SignupRequestDTO;
import com.example.mindbridge.entity.UserEntity;
import com.example.mindbridge.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO<UserEntity>> signup(@Valid @RequestBody SignupRequestDTO req) {
       UserEntity user = authService.signup(req);
        return ResponseEntity.ok(
                new ApiResponseDTO<>(true, "회원가입이 성공적으로 완료되었습니다.", user)
        );
    }

    @PostMapping("/login")
    public ApiResponseDTO<String> login(@RequestBody LoginRequestDTO req) {
        return authService.login(req);
    }
}
