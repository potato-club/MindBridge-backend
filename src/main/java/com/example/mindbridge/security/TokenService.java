package com.example.mindbridge.security;

import com.example.mindbridge.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    private final Map<Long, String> refreshTokenStore = new HashMap<>();

    public TokenService(JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    public Map<String, String> generateToken(long userId) {
        String accessToken = jwtTokenProvider.createAcessToken(userId);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId);

        refreshTokenStore.put(userId, refreshToken);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        return tokens;
    }

    public String refreshAcessToken(long userId, String refreshToken) {
        String storedToken = refreshTokenStore.get(userId);

        if (storedToken != null && storedToken.equals(refreshToken)
        && jwtTokenProvider.validateToken(refreshToken)) {
            return jwtTokenProvider.createAcessToken(userId);
        }
        throw new RuntimeException("유효하지 않은 리프레시 토큰입니다.");
    }
}
