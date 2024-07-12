package com.practice.hello.graduateboard.repository;



import com.practice.hello.graduateboard.entity.GraduateComment;
import com.practice.hello.graduateboard.entity.GraduateReply;
import com.practice.hello.secretboard.entity.SecretComment;
import com.practice.hello.secretboard.entity.SecretReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface GradutateReplyRepository extends JpaRepository<GraduateReply,Long> {
    Optional<GraduateReply> findById(Long id);

    Long countByGraduateComment(GraduateComment graduateComment);


    @Modifying
    @Transactional
    void deleteAllByGraduateCommentId(Long commentId); // Add this method
}
