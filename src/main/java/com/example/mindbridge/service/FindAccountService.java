package com.example.mindbridge.service;

import com.example.mindbridge.dto.FindIdRequestDTO;
import com.example.mindbridge.dto.FindPasswordRequestDTO;
import com.example.mindbridge.dto.ResetPasswordRequestDTO;
import com.example.mindbridge.entity.UserEntity;
import com.example.mindbridge.repository.UserRepository;
import com.example.mindbridge.util.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAccountService {
    private final UserRepository userRepository;
    private final SmsService smsService;
    private final PasswordEncoder passwordEncoder;

    public String findUserId(FindIdRequestDTO req) {
        UserEntity user = userRepository.findByUsernameAndPhoneNumber(req.getUsername(), req.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("계정이 존재하지 않습니다."));

        if (!smsService.verifyCode(req.getPhoneNumber(), req.getCode())) {
            throw new RuntimeException("인증번호가 일치하지 않습니다.");
        }

        return user.getUserid();
    }

    public boolean verifyForPasswordReset(FindPasswordRequestDTO req) {
        userRepository.findByUseridAndPhoneNumber(req.getUserid(), req.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("계정이 존재하지 않습니다."));

        if (!smsService.verifyCode(req.getPhoneNumber(), req.getCode())) {
            throw new RuntimeException("인증번호가 일치하지 않습니다.");
        }

        return true;
    }

    public void resetPassword(ResetPasswordRequestDTO req) {
        UserEntity user = userRepository.findByUserid(req.getUserid())
                .orElseThrow(() -> new RuntimeException("계정을 찾을 수 없습니다."));

        if (!req.getNewPassword().equals(req.getConfirmPassword())) {
            throw new RuntimeException("새 비밀번호가 일치하지 않습니다.");
        }






        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);
    }
}
