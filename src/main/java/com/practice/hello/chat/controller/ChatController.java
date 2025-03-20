package com.practice.hello.chat.controller;

import com.practice.hello.chat.dto.ChatMessageDTO;
import com.practice.hello.chat.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
@CrossOrigin(origins = "${spring.web.cors.allowed-origins}")
@RestController
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    public ChatController(SimpMessagingTemplate messagingTemplate, ChatService chatService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
    }

    @MessageMapping("/chat")
    public void processMessage(ChatMessageDTO message, Principal principal) {
        // 현재 로그인한 사용자 정보 가져오기
        String sender = principal.getName();  // 현재 로그인한 유저의 이메일 또는 사용자명

        // 메시지 처리 로직은 서비스에서 수행
        chatService.processMessage(message, sender);

        // 특정 사용자에게 메시지 전송
        messagingTemplate.convertAndSendToUser(message.receiver(), "/queue/messages", message);
    }
}
