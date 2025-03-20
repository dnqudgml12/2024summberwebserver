package com.practice.hello.chat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Unique chat room ID

    private LocalDateTime createdAt;

    // Optionally, you can add more fields like roomName, lastMessageTime, etc.

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatMessage> messages = new ArrayList<>(); // ChatMessages in this room

    public void setCreatedAt(LocalDateTime now) {
    }



    // Constructors, getters, and setters
}
