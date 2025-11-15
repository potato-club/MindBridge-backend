package com.mindbridge.controller;

import com.mindbridge.dto.ApiResponseDto;
import com.mindbridge.dto.FindIdRequestDto;
import com.mindbridge.dto.FindPasswordRequestDto;
import com.mindbridge.dto.ResetPasswordRequestDto;
import com.mindbridge.service.FindAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class FindAccountController {
    private final FindAccountService findAccountService;

    @PostMapping("/find-id")
    public ApiResponseDto<String> findId(@RequestBody FindIdRequestDto req) {
        String userid = findAccountService.findUserId(req);
        return new ApiResponseDto<>(true, "아이디 찾기 성공", userid);
    }

    @PostMapping("/find-password")
    public ApiResponseDto<String> findPassword(@RequestBody FindPasswordRequestDto req) {
        findAccountService.verifyForPasswordReset(req);
        return ApiResponseDto.success("인증 성공. 비밀번호 재설정 가능", null);
    }

    @PostMapping("reset-password")
    public ApiResponseDto<Void> resetPassword(@Valid @RequestBody ResetPasswordRequestDto req) {
        findAccountService.resetPassword(req);
        return ApiResponseDto.success("비밀번호가 성공적으로 변경되었습니다.", null);
    }
}
