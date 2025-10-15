package com.mindbridge.controller;

import com.mindbridge.dto.ApiResponseDTO;
import com.mindbridge.dto.SignupRequestDTO;
import com.mindbridge.entity.UserEntity;
import com.mindbridge.service.AuthService;
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
}
