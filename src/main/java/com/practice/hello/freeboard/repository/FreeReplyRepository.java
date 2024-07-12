package com.practice.hello.freeboard.repository;



import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freeboard.entity.FreeReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface FreeReplyRepository extends JpaRepository<FreeReply,Long> {
    Optional<FreeReply> findById(Long id);

    Long countByFreeComment(FreeComment freeComment);


    @Modifying
    @Transactional
    void deleteAllByFreeCommentId(Long commentId); // Add this method
}
