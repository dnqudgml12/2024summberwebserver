package com.practice.hello.graduateboard.repository;



import com.practice.hello.graduateboard.entity.GraduateBoard;
import com.practice.hello.graduateboard.entity.GraduateComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradutateCommentRepository extends JpaRepository<GraduateComment,Long> {
    Optional<GraduateComment> findById(Long id);


    Long countByGraduateBoard(GraduateBoard graduateBoard); // countByFreeBoard로 변경

    @Query("SELECT c FROM GraduateComment c WHERE c.graduateBoard.id = :boardId")
    List<GraduateComment> findByBoardId(@Param("boardId") Long boardId);



}
