package com.example.mindbridge.controller;

import com.example.mindbridge.dto.ApiResponseDTO;
import com.example.mindbridge.dto.FindIdRequestDTO;
import com.example.mindbridge.dto.FindPasswordRequestDTO;
import com.example.mindbridge.dto.ResetPasswordRequestDTO;
import com.example.mindbridge.service.FindAccountService;
import com.example.mindbridge.util.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class FindAccoutController {
    private final FindAccountService findAccountService;
    private final SmsService smsService;

    @PostMapping("/send-code")
    public ApiResponseDTO<String> sendCode(@RequestParam String phoneNumber) {
        String code = smsService.sendCode(phoneNumber);
        return new ApiResponseDTO<>(true, "인증번호 발송 성공", code);
    }

    @PostMapping("/find-id")
    public ApiResponseDTO<String> findId(@RequestBody FindIdRequestDTO req) {
        String userid = findAccountService.findUserId(req);
        return new ApiResponseDTO<>(true, "아이디 찾기 성공", userid);
    }

    @PostMapping("/find-password")
    public ApiResponseDTO<String> findPassword(@RequestBody FindPasswordRequestDTO req) {
        findAccountService.verifyForPasswordReset(req);
        return ApiResponseDTO.success("인증 성공. 비밀번호 재설정 가능", null);
    }

    @PostMapping("reset-password")
    public ApiResponseDTO<Void> resetPassword(@RequestBody ResetPasswordRequestDTO req) {
        findAccountService.resetPassword(req);
        return ApiResponseDTO.success("비밀번호가 성공적으로 변경되었습니다.", null);
    }
}
