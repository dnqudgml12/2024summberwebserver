package com.practice.hello.chat.dto;

import com.practice.hello.chat.entity.ChatMessage;
import com.practice.hello.member.entity.Member;

import java.time.LocalDateTime;

public record ChatMessageDTO(String sender, String receiver, String content) {

    public ChatMessage toEntity(Member senderMember, Member receiverMember) {
        return ChatMessage.builder()
                .sender(senderMember.getNickname()) // 보내는 사람의 닉네임 설정
                .receiver(receiverMember.getNickname()) // 받는 사람의 닉네임 설정
                .content(content)
                .timestamp(LocalDateTime.now()) // 메시지가 생성된 시간을 현재 시간으로 설정
                .build();
    }
}
