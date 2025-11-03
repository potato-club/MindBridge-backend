package com.mindbridge.controller;

import com.mindbridge.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sms")
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<String> sendAuthCode(
            @RequestParam String phone
    ){
        messageService.sendAuthCode(phone);

        return ResponseEntity.ok("인증번호가 전송되었습니다.");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode(
            @RequestParam String phone,
            @RequestParam String code
    ) {
        boolean result = messageService.verifyAuthCode(phone, code);

        return ResponseEntity.ok(result ? "인증 성공" : "인증 실패");
    }

}
