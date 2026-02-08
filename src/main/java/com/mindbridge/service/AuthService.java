package com.mindbridge.service;

import com.mindbridge.dto.LoginTokens;
import com.mindbridge.dto.requestDto.LoginRequestDto;
import com.mindbridge.dto.responseDto.TokenResponseDto;
import com.mindbridge.dto.requestDto.SignupRequestDto;
import com.mindbridge.entity.user.RefreshToken;
import com.mindbridge.entity.user.UserEntity;
import com.mindbridge.error.ErrorCode;
import com.mindbridge.error.customExceptions.CustomException;
import com.mindbridge.error.customExceptions.UnauthorizedException;
import com.mindbridge.error.customExceptions.UserNotFoundException;
import com.mindbridge.jwt.JwtUtil;
import com.mindbridge.repository.RefreshTokenRepository;
import com.mindbridge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AuthService{

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${jwt.token.refresh-expiration-time}")
    private long refreshExpirationTime;


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
                .build();

        return userRepository.save(user);
    }

@Transactional
    public LoginTokens login(LoginRequestDto req) {

        UserEntity user = userRepository.findByLoginId(req.loginId())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new UnauthorizedException(ErrorCode.INVALID_PASSWORD);
        }

        Long userId = user.getId();
        String username = user.getUsername();
        String nickname = user.getNickname();

        String accessToken = jwtUtil.createAccessToken(userId, username, nickname);
        String refreshToken = jwtUtil.createRefreshToken(userId);

        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(refreshExpirationTime);

        RefreshToken saveToken = refreshTokenRepository.findByUserId(userId).orElse(null);

        if(saveToken == null) {
            refreshTokenRepository.save(
                    RefreshToken.builder()
                            .userId(userId)
                            .token(passwordEncoder.encode(refreshToken))
                            .expiredAt(expiresAt)
                            .build()
            );
        } else {
            saveToken.update(passwordEncoder.encode(refreshToken), expiresAt);
        }

        return new LoginTokens(accessToken, refreshToken);
    }

    public TokenResponseDto reissue(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new UnauthorizedException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        Long userId = jwtUtil.getUserId(refreshToken);
        RefreshToken savedToken = refreshTokenRepository.findByUserId(userId).orElseThrow(() ->
                new UserNotFoundException(ErrorCode.INVALID_REFRESH_TOKEN));

        if(!passwordEncoder.matches(refreshToken, savedToken.getToken())) {
            throw new UnauthorizedException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        if (savedToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(savedToken);
            throw new UnauthorizedException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        String newAccessToken = jwtUtil.createAccessToken(userId, user.getUsername(), user.getNickname());
        String newRefreshToken = jwtUtil.createRefreshToken(userId);

        savedToken.update(
                passwordEncoder.encode(newRefreshToken),
                LocalDateTime.now()
                        .plusSeconds(refreshExpirationTime)
        );
        return new TokenResponseDto(newAccessToken, newRefreshToken);
    }

    public boolean isDuplicateLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    public boolean isDuplicateNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Transactional
    public void withdraw(Long userId, String password) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        userRepository.delete(user);
    }
}
