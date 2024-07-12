package com.practice.hello.repository.board;



import com.practice.hello.entity.board.Comment;
import com.practice.hello.entity.board.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply,Long> {
    Optional<Reply> findById(Long id);

    long countByComment(Comment comment);


    @Modifying
    @Transactional
    void deleteAllByCommentId(Long commentId); // Add this method
}
