package com.practice.hello.circle.repository;


import com.practice.hello.circle.entity.CircleBoard;
import com.practice.hello.circle.entity.CircleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CircleCommentRepository extends JpaRepository<CircleComment,Long> {
    Optional<CircleComment> findById(Long id);


    Long countByCircleBoard(CircleBoard circleBoard); // countByFreeBoard로 변경

    @Query("SELECT c FROM CircleComment c WHERE c.circleBoard.id = :boardId")
    List<CircleComment> findByBoardId(@Param("boardId") Long boardId);



}
