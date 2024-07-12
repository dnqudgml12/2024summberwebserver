package com.practice.hello.repository.board;


import com.practice.hello.entity.board.Board;
import com.practice.hello.entity.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<Comment> findById(Long id);


    long countByBoard(Board board);
    @Query("SELECT c FROM Comment c WHERE c.board.id = :boardId")
    List<Comment> findByBoardId(@Param("boardId") Long boardId);



}
