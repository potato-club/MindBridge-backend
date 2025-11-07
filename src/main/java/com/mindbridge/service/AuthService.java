package com.mindbridge.service;

import com.mindbridge.dto.RequestDto.LoginRequestDto;
import com.mindbridge.dto.ResponseDto.ApiResponseDto;
import com.mindbridge.dto.ResponseDto.LoginResponseDto;
import com.mindbridge.dto.ResponseDto.TokenResponseDto;
import com.mindbridge.dto.RequestDto.SignupRequestDto;
import com.mindbridge.dto.ResponseDto.UserResponseDto;
import com.mindbridge.entity.UserEntity;
import com.mindbridge.error.ErrorCode;
import com.mindbridge.error.customExceptions.CustomException;
import com.mindbridge.error.customExceptions.UnauthorizedException;
import com.mindbridge.error.customExceptions.UserNotFoundException;
import com.mindbridge.jwt.JwtUtil;
import com.mindbridge.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${jwt.token.refresh-expiration-time}")
    private Long refreshExpMs;

    public UserEntity signup(SignupRequestDto req) {
        // 아이디 중복 확인
        if (userRepository.existsByLoginId(req.getLoginId())) {
            throw new CustomException(ErrorCode.DUPLICATE_LOGIN_ID);
        }

        // 닉네임 중복 확인
        if (userRepository.existsByNickname(req.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }

        // 전화번호 중복 확인
        if (userRepository.existsByPhoneNumber(req.getPhoneNumber())) {
            throw new CustomException(ErrorCode.DUPLICATE_PHONE_NUMBER);
        }

        // 비밀번호 확인
        if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        // 유저 저장
        UserEntity user = UserEntity.builder()
                .loginId(req.getLoginId())
                .username(req.getUsername())
                .nickname(req.getNickname())
                .password(passwordEncoder.encode(req.getPassword()))
                .phoneNumber(req.getPhoneNumber())
                .gender(req.getGender())
                .birthDate(req.getBirthDate())
                .verified(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto req, HttpServletResponse httpServletResponse) {
        String loginId = req.loginId();
        String password = req.password();

        UserEntity user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthorizedException(ErrorCode.INVALID_PASSWORD);
        }

        Long userId = user.getId();
        String username = user.getUsername();
        String nickname = user.getNickname();

        String accessToken = jwtUtil.createAccessToken(userId, username, nickname);
        String refreshToken = jwtUtil.createRefreshToken(userId);

        httpServletResponse.setHeader("Authorization", "Bearer " + accessToken);

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(refreshExpMs)
                .sameSite("None")
                .build();
        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return new LoginResponseDto(accessToken);
    }

    public TokenResponseDto reissue(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new UnauthorizedException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        Long userId = jwtUtil.getUserId(refreshToken);
        String username = jwtUtil.getUsername(refreshToken);
        String nickname = jwtUtil.getNickname(refreshToken);

        String newAccessToken = jwtUtil.createAccessToken(userId, username, nickname);
        return new TokenResponseDto(newAccessToken);
    }

    public boolean isDuplicateLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    public boolean isDuplicateNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

//    public void logout(String loginId) {
//        UserEntity user = userRepository.findByLoginId(loginId)
//                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
//
//        user.setRefreshToken(null);
//        userRepository.save(user);
//    }
}
