package com.example.mindbridge.controller;

import com.example.mindbridge.dto.ApiResponseDTO;
import com.example.mindbridge.dto.LoginRequestDTO;
import com.example.mindbridge.service.AuthService;
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

    @PostMapping("/login")
    public ApiResponseDTO<String> login(@RequestBody LoginRequestDTO req) {
        return authService.login(req);
    }
}
