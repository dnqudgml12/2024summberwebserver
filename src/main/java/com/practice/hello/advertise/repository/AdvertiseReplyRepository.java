package com.practice.hello.advertise.repository;



import com.practice.hello.advertise.entity.AdvertiseComment;
import com.practice.hello.advertise.entity.AdvertiseReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AdvertiseReplyRepository extends JpaRepository<AdvertiseReply,Long> {
    Optional<AdvertiseReply> findById(Long id);

    Long countByAdvertiseComment(AdvertiseComment advertiseComment);


    @Modifying
    @Transactional
    void deleteAllByAdvertiseCommentId(Long commentId); // Add this method
}
