package com.practice.hello.chat.controller;

import com.practice.hello.chat.dto.ChatRoomRequestDTO;
import com.practice.hello.chat.entity.ChatRoom;
import com.practice.hello.chat.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatRoomController {

    private final ChatService chatService;

    public ChatRoomController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat-room")
    public ResponseEntity<?> createOrGetChatRoom(@RequestBody ChatRoomRequestDTO chatRoomRequest) {
        // Use the chat service to get or create a chat room for the two participants
        ChatRoom chatRoom = chatService.getOrCreateChatRoom(chatRoomRequest.userA(), chatRoomRequest.userB());

        // Return the chat room's ID in the response
        return ResponseEntity.ok(Map.of("roomId", chatRoom.getId()));
    }
}
