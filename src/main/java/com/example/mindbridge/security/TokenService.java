package com.example.mindbridge.security;

import com.example.mindbridge.entity.RefreshTokenEntity;
import com.example.mindbridge.repository.RefreshTokenRepository;
import com.example.mindbridge.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenService(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public Map<String, String> generateToken(long userId) {
        String accessToken = jwtTokenProvider.createAccessToken(userId);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId);

        refreshTokenRepository.findByUserId(userId)
                .ifPresentOrElse(
                        token -> token.setToken(refreshToken),
                        () -> refreshTokenRepository.save(new RefreshTokenEntity(null, userId, refreshToken))
                );

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        return tokens;
    }

    public String refreshAccessToken(long userId, String refreshToken) {
        RefreshTokenEntity storedToken = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("저장된 리프레쉬 토큰이 없습니다."));

        if (storedToken.getToken().equals(refreshToken)
        && jwtTokenProvider.validateToken(refreshToken)) {
            return jwtTokenProvider.createAccessToken(userId);
        }
        throw new RuntimeException("유효하지 않은 리프레시 토큰입니다.");
    }
}
