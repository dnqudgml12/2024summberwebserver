package com.practice.hello.chat.repository;


import com.practice.hello.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT c FROM ChatRoom c JOIN c.messages m WHERE (m.sender = :userA AND m.receiver = :userB) OR (m.sender = :userB AND m.receiver = :userA)")
    Optional<ChatRoom> findByParticipants(@Param("userA") String userA, @Param("userB") String userB);
}
