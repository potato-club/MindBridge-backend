package com.mindbridge.service;

import com.mindbridge.dto.FindIdRequestDto;
import com.mindbridge.dto.FindPasswordRequestDto;
import com.mindbridge.dto.ResetPasswordRequestDto;
import com.mindbridge.entity.UserEntity;
import com.mindbridge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAccountService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String findUserId(FindIdRequestDto req) {
        UserEntity user = userRepository.findByUsernameAndPhoneNumber(req.getUsername(), req.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("계정이 존재하지 않습니다."));

        return user.getUserid();
    }

    public boolean verifyForPasswordReset(FindPasswordRequestDto req) {
        userRepository.findByUseridAndPhoneNumber(req.getUserid(), req.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("계정이 존재하지 않습니다."));

        return true;
    }

    public void resetPassword(ResetPasswordRequestDto req) {
        UserEntity user = userRepository.findByUserid(req.getUserid())
                .orElseThrow(() -> new RuntimeException("계정을 찾을 수 없습니다."));

        if (!req.getNewPassword().equals(req.getConfirmPassword())) {
            throw new RuntimeException("새 비밀번호가 일치하지 않습니다.");
        }

        String newPw = req.getNewPassword();

        if (newPw.length() < 8) {
            throw new RuntimeException("비밀번호는 8자 이상이어야 합니다.");
        }

        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,}$";
        if (!newPw.matches(regex)) {
            throw new RuntimeException("비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다.");
        }

        if (!passwordEncoder.matches(newPw, user.getPassword())) {
            throw new RuntimeException("이전 비밀번호와 동일한 비밀번호는 사용할 수 없습니다.");
        }

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);
    }
}
