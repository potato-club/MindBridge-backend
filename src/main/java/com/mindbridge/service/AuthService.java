package com.mindbridge.service;

import com.mindbridge.dto.LoginRequestDto;
import com.mindbridge.dto.ResponseDto.LoginResponseDto;
import com.mindbridge.dto.ResponseDto.TokenResponseDto;
import com.mindbridge.dto.SignupRequestDto;
import com.mindbridge.entity.UserEntity;
import com.mindbridge.error.ErrorCode;
import com.mindbridge.error.customExceptions.UnauthorizedException;
import com.mindbridge.error.customExceptions.UserNotFoundException;
import com.mindbridge.jwt.JwtUtil;
import com.mindbridge.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        // 닉네임 중복 확인
        if (userRepository.existsByNickname(req.getNickname())) {
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        }

        // 전화번호 중복 확인
        if (userRepository.existsByPhoneNumber(req.getPhoneNumber())) {
            throw new RuntimeException("이미 등록된 전화번호입니다.");
        }

        // 비밀번호 확인
        if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 유저 저장
        UserEntity user = UserEntity.builder()
                .loginId(req.getLoginId())
                .username(req.getUsername())
                .nickname(req.getUsername())
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

//    public LoginResponseDto login(LoginRequestDto req, HttpServletResponse httpServletResponse) {
//        String loginId = req.loginId();
//        String password = req.password();
//
//        UserEntity userEntity = userRepository.findByLoginId(loginId)
//                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
//
//        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
//            throw new UnauthorizedException(ErrorCode.INVALID_PASSWORD);
//        }
//
//        Long userId = userEntity.getId();
//        String username = userEntity.getUsername();
//        String nickname = userEntity.getNickname();
//
//        String accessToken = jwtUtil.createAccessToken(userId, username, nickname);
//        String refreshToken = jwtUtil.createRefreshToken(userId);
//
//        httpServletResponse.setHeader("Authorization", "Bearer " + accessToken);
//
//        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
//                .httpOnly(true)
//                .secure(false)
//                .path("/")
//                .maxAge(refreshExpMs)
//                .sameSite("None")
//                .build();
//        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
//
//        return new LoginResponseDto(accessToken);
//
//    }

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
}
