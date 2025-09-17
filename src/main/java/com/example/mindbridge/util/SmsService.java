package com.example.mindbridge.util;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SmsService {
    private final Map<String, String> codeStorage = new HashMap<>();

    public String sendCode(String phoneNumber) {
        String code = String.valueOf((int)(Math.random() * 900000) + 100000);
        codeStorage.put(phoneNumber, code);
        System.out.println("인증번호: [" + code + "] 전송됨 ->" + phoneNumber);
        return code;
    }

    public boolean verifyCode(String phoneNumber, String code) {
        return code.equals(codeStorage.get(phoneNumber));
    }
}
