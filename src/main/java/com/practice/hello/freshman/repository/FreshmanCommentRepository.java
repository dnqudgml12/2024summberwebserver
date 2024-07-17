package com.practice.hello.freshman.repository;


import com.practice.hello.freshman.entity.Freshman;
import com.practice.hello.freshman.entity.FreshmanComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FreshmanCommentRepository extends JpaRepository<FreshmanComment,Long> {
    Optional<FreshmanComment> findById(Long id);


    Long countByFreshman(Freshman freshman); // countByFreeBoard로 변경

    @Query("SELECT c FROM FreshmanComment c WHERE c.freshman.id = :boardId")
    List<FreshmanComment> findByBoardId(@Param("boardId") Long boardId);



}
