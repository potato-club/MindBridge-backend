package com.mindbridge.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private  SecretKey secretKey;

    public JwtAuthenticationFilter(@Value("${jwt.secret}") String signingKey) {
        this.secretKey = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
        //        this.secretKey = new SecretKeySpec(signingKey.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        AntPathMatcher pathMatcher = new AntPathMatcher();

        // 특정 경로를 필터에서 제외
        if (path.equals("/login")
                || path.equals("/signup")
                || path.equals("/api/auth/login")
                || path.equals("/api/auth/reissue")
                || pathMatcher.match("/swagger-ui/**", path)
                || pathMatcher.match("/v3/api-docs/**", path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = request.getHeader("Authorization");

        if (jwt == null || !jwt.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT token missing or invalid");
            return;
        }

        // 'Bearer ' 접두어를 제거하고 실제 JWT 토큰만 가져오기
        jwt = jwt.substring(7);

        // 토큰에서 클레임을 얻고 서명 검증.
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        Number userIdNumber = (Number) claims.get("userId");
        Long userId = userIdNumber.longValue();
        String username = (String) claims.get("username");
        String nickname = (String) claims.get("nickname");

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));//임시로 role설정

        CustomUserDetails userDetails = new CustomUserDetails(
                userId, username, nickname, authorities);

        UsernamePasswordAuthentication authentication = new UsernamePasswordAuthentication(userDetails, null, authorities);

        // SecurityContext에 Authentication 객체 추가
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        filterChain.doFilter(request, response);    // 필터 체인의 다음 필터 호룿
    }
}
