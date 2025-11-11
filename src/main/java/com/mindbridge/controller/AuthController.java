package com.mindbridge.controller;

import com.mindbridge.dto.RequestDto.*;
import com.mindbridge.dto.ResponseDto.ApiResponseDto;
import com.mindbridge.dto.ResponseDto.LoginResponseDto;
import com.mindbridge.dto.ResponseDto.TokenResponseDto;
import com.mindbridge.dto.ResponseDto.UserResponseDto;
import com.mindbridge.entity.UserEntity;
import com.mindbridge.error.ErrorCode;
import com.mindbridge.error.customExceptions.CustomException;
import com.mindbridge.jwt.CustomUserDetails;
import com.mindbridge.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/check-id")
    public ResponseEntity<ApiResponseDto<String>> checkDuplicateLoginId(@RequestBody CheckLoginIdRequestDto requestDto) {
        boolean isDuplicate = authService.isDuplicateLoginId(requestDto.getLoginId());

        if (isDuplicate) {
            throw new CustomException(ErrorCode.DUPLICATE_LOGIN_ID);
        }
        return ResponseEntity.ok(new ApiResponseDto<>(true, "사용 가능한 아이디입니다.", null));
    }

    @PostMapping("/check-nickname")
    public ResponseEntity<ApiResponseDto<String>> checkDuplicateNickname(@Valid @RequestBody CheckNicknameRequestDto requestDto) {
        boolean isDuplicate = authService.isDuplicateNickname(requestDto.getNickname());

        if (isDuplicate) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }
        return ResponseEntity.ok(new ApiResponseDto<>(true, "사용 가능한 닉네임입니다.", null));
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

    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDto<String>> logout(
            @RequestHeader(value = "Authorization", required = false) String token
    ) {
        return ResponseEntity.ok(ApiResponseDto.success("로그아웃이 완료되었습니다."));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponseDto<Void>> withdraw(@RequestBody WithdrawRequestDto request,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {

        authService.withdraw(userDetails.getId(), request.getPassword());
        return ResponseEntity.ok(ApiResponseDto.success("회원탈퇴가 완료되었습니다.", null));
    }
}