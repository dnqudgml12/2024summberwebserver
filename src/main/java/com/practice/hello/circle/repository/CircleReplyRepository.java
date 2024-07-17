package com.practice.hello.circle.repository;



import com.practice.hello.circle.entity.CircleComment;
import com.practice.hello.circle.entity.CircleReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CircleReplyRepository extends JpaRepository<CircleReply,Long> {
    Optional<CircleReply> findById(Long id);

    Long countByCircleComment(CircleComment circleComment);


    @Modifying
    @Transactional
    void deleteAllByCircleCommentId(Long commentId); // Add this method
}
