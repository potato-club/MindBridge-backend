package com.example.mindbridge.controller;

import com.example.mindbridge.dto.SignupRequest;
import com.example.mindbridge.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest req) {
//
//    }
}
