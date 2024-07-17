package com.practice.hello.information.repository;


import com.practice.hello.information.entity.InformationBoard;
import com.practice.hello.information.entity.InformationComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InformationCommentRepository extends JpaRepository<InformationComment,Long> {
    Optional<InformationComment> findById(Long id);


    Long countByInformationBoard(InformationBoard informationBoard); // countByFreeBoard로 변경

    @Query("SELECT c FROM InformationComment c WHERE c.informationBoard.id = :boardId")
    List<InformationComment> findByBoardId(@Param("boardId") Long boardId);



}
