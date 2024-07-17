package com.practice.hello.freshman.repository;



import com.practice.hello.freshman.entity.FreshmanComment;
import com.practice.hello.freshman.entity.FreshmanReply;
import com.practice.hello.freshman.entity.FreshmanReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface FreshmanReplyRepository extends JpaRepository<FreshmanReply,Long> {
    Optional<FreshmanReply> findById(Long id);

    Long countByFreshmanComment(FreshmanComment freshmanComment);


    @Modifying
    @Transactional
    void deleteAllByFreshmanCommentId(Long commentId); // Add this method
}
