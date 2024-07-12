package com.practice.hello.freeboard.repository;


import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.entity.FreeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FreeCommentRepository extends JpaRepository<FreeComment,Long> {
    Optional<FreeComment> findById(Long id);


    Long countByFreeBoard(FreeBoard freeBoard); // countByFreeBoard로 변경

    @Query("SELECT c FROM FreeComment c WHERE c.freeBoard.id = :boardId")
    List<FreeComment> findByBoardId(@Param("boardId") Long boardId);



}
