package com.mindbridge.service;

import com.mindbridge.dto.FindIdRequestDto;
import com.mindbridge.dto.FindPasswordRequestDto;
import com.mindbridge.dto.ResetPasswordRequestDto;
import com.mindbridge.entity.UserEntity;
import com.mindbridge.error.ErrorCode;
import com.mindbridge.error.customExceptions.CustomException;
import com.mindbridge.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAccountService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String findUserId(FindIdRequestDto req) {
        UserEntity user = userRepository.findByUsernameAndPhoneNumber(req.username(), req.phoneNumber())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return user.getUserid();
    }

    public void verifyForPasswordReset(FindPasswordRequestDto req) {
        userRepository.findByUseridAndPhoneNumber(req.userid(), req.phoneNumber())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public void resetPassword(ResetPasswordRequestDto req) {
        UserEntity user = userRepository.findByUserid(req.userid())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!req.newPassword().equals(req.confirmPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        String newPw = req.newPassword();

        if (newPw.length() < 8) {
            throw new CustomException(ErrorCode.PASSWORD_TOO_SHORT);
        }

        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,}$";
        if (!newPw.matches(regex)) {
            throw new CustomException(ErrorCode.PASSWORD_REGEX_NOT_VALID);
        }

        if (passwordEncoder.matches(newPw, user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_SAME_AS_OLD);
        }

        user.setPassword(passwordEncoder.encode(newPw));
        userRepository.save(user);
    }
}
