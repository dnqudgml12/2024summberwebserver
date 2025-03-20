package com.practice.hello.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 1:1 채팅을 위한 큐 설정
        config.enableSimpleBroker("/queue"); // 클라이언트에게 메시지를 전달할 브로커 경로 설정
        config.setApplicationDestinationPrefixes("/app"); // 클라이언트가 메시지를 보낼 경로 설정
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket 연결 설정 (특정 도메인만 허용)
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("http://localhost:*") // 와일드카드 도메인 허용 (Spring 5.3 이상)
                .withSockJS(); // SockJS 지원
    }
}
