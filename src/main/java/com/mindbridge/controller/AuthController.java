package com.mindbridge.controller;

import com.mindbridge.dto.RequestDto.LoginRequestDto;
import com.mindbridge.dto.ResponseDto.ApiResponseDto;
import com.mindbridge.dto.ResponseDto.LoginResponseDto;
import com.mindbridge.dto.ResponseDto.TokenResponseDto;
import com.mindbridge.dto.RequestDto.SignupRequestDto;
import com.mindbridge.dto.ResponseDto.UserResponseDto;
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
    public ResponseEntity<ApiResponseDto<UserResponseDto>> signup(@Valid @RequestBody SignupRequestDto req) {
        UserEntity user = authService.signup(req);
        UserResponseDto dto = new UserResponseDto(user);
        return ResponseEntity.ok(
                new ApiResponseDto<>(true, "회원가입이 성공적으로 완료되었습니다.", dto)
        );
    }

    @GetMapping("/check-id")
    public ResponseEntity<Boolean> checkDuplicateId(@RequestBody String loginId) {
        boolean isDuplicate = authService.isDuplicateLoginId(loginId);
        return ResponseEntity.ok(isDuplicate);
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<Boolean> checkDuplicateNickname(@RequestBody String nickname) {
        boolean isDuplicate = authService.isDuplicateNickname(nickname);
        return ResponseEntity.ok(isDuplicate);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto req,
            HttpServletResponse httpServletResponse) {
        LoginResponseDto loginResponseDto = authService.login(req, httpServletResponse);
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDto> reissue(
            @CookieValue("refreshToken") String refreshToken) {
        TokenResponseDto tokenResponseDto = authService.reissue(refreshToken);

        return ResponseEntity.ok(tokenResponseDto);
    }

//    @PostMapping("/logout/{loginId}")
//    public ResponseEntity<ApiResponseDto> logout(
//            @PathVariable String loginId
//    )
}