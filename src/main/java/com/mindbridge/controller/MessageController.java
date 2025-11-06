package com.mindbridge.controller;

import com.mindbridge.dto.RequestDto.VerificationRequestDto;
import com.mindbridge.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sms")
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<String> sendAuthCode(
            @RequestBody String phone
    ){
        messageService.sendAuthCode(phone);

        return ResponseEntity.ok("인증번호가 전송되었습니다.");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode(
            @RequestBody VerificationRequestDto verifyRequest
            ) {
        boolean result = messageService.verifyAuthCode(verifyRequest.phone(), verifyRequest.code());

        return ResponseEntity.ok(result ? "인증 성공" : "인증 실패");
    }

}
