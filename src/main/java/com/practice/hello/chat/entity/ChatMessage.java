package com.practice.hello.chat.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;  // 보낸 사람의 닉네임
    private String receiver;  // 받는 사람의 닉네임
    private String content;  // 메시지 내용
    private LocalDateTime timestamp;  // 메시지 전송 시간
    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;  // Foreign key to ChatRoom
    @Builder
    public ChatMessage(String sender, String receiver, String content, LocalDateTime timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = timestamp;
    }
}
