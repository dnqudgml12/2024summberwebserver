package com.practice.hello.social.repository;



import com.practice.hello.social.entity.SocialComment;
import com.practice.hello.social.entity.SocialReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SocialReplyRepository extends JpaRepository<SocialReply,Long> {
    Optional<SocialReply> findById(Long id);

    Long countBySocialComment(SocialComment socialComment);


    @Modifying
    @Transactional
    void deleteAllBySocialCommentId(Long commentId); // Add this method
}
