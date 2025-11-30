package com.mindbridge.controller;

import static com.mindbridge.config.SecurityConstants.*;

import com.mindbridge.dto.LoginTokens;
import com.mindbridge.dto.requestDto.*;
import com.mindbridge.dto.responseDto.ApiResponseDto;
import com.mindbridge.dto.responseDto.LoginResponseDto;
import com.mindbridge.dto.responseDto.TokenResponseDto;
import com.mindbridge.dto.responseDto.UserResponseDto;
import com.mindbridge.entity.user.UserEntity;
import com.mindbridge.error.ErrorCode;
import com.mindbridge.error.customExceptions.CustomException;
import com.mindbridge.jwt.CustomUserDetails;
import com.mindbridge.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Value("${jwt.refresh-expiration-ms}")
    private Long refreshExpMs;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> signup(@Valid @RequestBody SignupRequestDto req) {
        UserEntity user = authService.signup(req);
        UserResponseDto dto = new UserResponseDto(user);
        return ResponseEntity.ok(
                new ApiResponseDto<>(true, "회원가입이 성공적으로 완료되었습니다.", dto)
        );
    }

    @PostMapping("/check-id")
    public ResponseEntity<ApiResponseDto<Boolean>> checkDuplicateLoginId(@Valid @RequestBody CheckLoginIdRequestDto requestDto) {
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
    public ResponseEntity<ApiResponseDto<LoginResponseDto>> login(
            @Valid @RequestBody LoginRequestDto req) {

        LoginTokens tokens = authService.login(req);

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", tokens.refreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(refreshExpMs)
                .sameSite("None")
                .build();

        return ResponseEntity.ok()
                .header(AUTHORIZATION_HEADER, BEARER_PREFIX + tokens.accessToken())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(ApiResponseDto.success("로그인 성공", new LoginResponseDto(tokens.accessToken())));
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