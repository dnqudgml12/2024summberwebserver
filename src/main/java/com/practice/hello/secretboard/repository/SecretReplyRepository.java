package com.practice.hello.secretboard.repository;



import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freeboard.entity.FreeReply;
import com.practice.hello.secretboard.entity.SecretComment;
import com.practice.hello.secretboard.entity.SecretReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SecretReplyRepository extends JpaRepository<SecretReply,Long> {
    Optional<SecretReply> findById(Long id);

    Long countBySecretComment(SecretComment secretComment);


    @Modifying
    @Transactional
    void deleteAllBySecretCommentId(Long commentId); // Add this method
}
