package com.mindbridge.controller;

import com.mindbridge.dto.ApiResponseDto;
import com.mindbridge.dto.LoginRequestDto;
import com.mindbridge.dto.ResponseDto.LoginResponseDto;
import com.mindbridge.dto.ResponseDto.TokenResponseDto;
import com.mindbridge.dto.SignupRequestDto;
import com.mindbridge.entity.UserEntity;
import com.mindbridge.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto<UserEntity>> signup(@Valid @RequestBody SignupRequestDto req) {
        UserEntity user = authService.signup(req);
        return ResponseEntity.ok(
                new ApiResponseDto<>(true, "회원가입이 성공적으로 완료되었습니다.", user)
        );
    }

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponseDto> login(
//            @RequestBody LoginRequestDto req,
//            HttpServletResponse httpServletResponse) {
//        LoginResponseDto loginResponseDto = authService.login(req, httpServletResponse);
//        return ResponseEntity.ok(loginResponseDto);
//
//    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDto> reissue(
            @CookieValue("refreshToken") String refreshToken) {
        TokenResponseDto tokenResponseDto = authService.reissue(refreshToken);

        return ResponseEntity.ok(tokenResponseDto);
    }
}