package com.mindbridge.config;

import com.mindbridge.config.websocket.WebSocketAuthChannelInterceptor;
import com.mindbridge.config.websocket.WebSocketAuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketAuthInterceptor webSocketAuthInterceptor;
    private final WebSocketAuthChannelInterceptor webSocketAuthChannelInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")            // 웹소켓 연결 주소를 /ws/chat 로 만듦
                .setAllowedOriginPatterns("*");      // cors 허용
    }

    // STOMP 메시지가 들어가고, 어디로 나가는 곳을 지정하는 라우팅 규칙 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub");         // 클라이언트 → 서버 방향 메시지(prefix)
        registry.enableSimpleBroker("/sub");       // 서버 → 클라이언트 방향 메시지(prefix)
    }

    // 채널 인터셉터 등록
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketAuthChannelInterceptor);
    }
}