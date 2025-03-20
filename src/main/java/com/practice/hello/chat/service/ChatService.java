package com.practice.hello.chat.service;

import com.practice.hello.chat.dto.ChatMessageDTO;
import com.practice.hello.chat.entity.ChatMessage;
import com.practice.hello.chat.entity.ChatRoom;
import com.practice.hello.chat.repository.ChatMessageRepository;
import com.practice.hello.chat.repository.ChatRoomRepository;
import com.practice.hello.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.practice.hello.member.entity.Member;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j // Lombok을 사용하여 로깅 기능을 활성화
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;  // MemberRepository 사용
    public ChatRoom getOrCreateChatRoom(String userA, String userB) {
        // Check if a room already exists between two users
        Optional<ChatRoom> existingRoom = chatRoomRepository.findByParticipants(userA, userB);
        return existingRoom.orElseGet(() -> {
            ChatRoom newRoom = new ChatRoom();
            newRoom.setCreatedAt(LocalDateTime.now());
            return chatRoomRepository.save(newRoom);
        });
    }

    public void processMessage(ChatMessageDTO messageDTO, String senderEmail) {
        log.info("Processing message from senderEmail: {} to receiver: {}", senderEmail, messageDTO.receiver());

        // senderEmail을 이용해 로그인된 사용자 정보 가져오기
        Member senderMember = memberRepository.findByUid(senderEmail)
                .orElseThrow(() -> new IllegalArgumentException("이 이메일에 해당하는 사람이 없습니다."));

        // receiver의 닉네임을 이용해 수신자 정보 가져오기
        Member receiverMember = memberRepository.findByNickname(messageDTO.receiver())
                .orElseThrow(() -> {
                    log.error("Receiver {} not found in database.", messageDTO.receiver());
                    return new IllegalArgumentException("해당 닉네임에 해당하는 사람이 없습니다.");
                });

        ChatRoom chatRoom = getOrCreateChatRoom(senderMember.getNickname(), receiverMember.getNickname());

        ChatMessage chatMessage = messageDTO.toEntity(senderMember, receiverMember);
        chatMessage.setChatRoom(chatRoom);

        log.info("New chat message from {} to {}: {}", senderMember.getNickname(), receiverMember.getNickname(), chatMessage.getContent());

        // 메시지 엔티티 저장 (예: 메시지 기록을 DB에 저장)
        chatMessageRepository.save(chatMessage);
    }

}
