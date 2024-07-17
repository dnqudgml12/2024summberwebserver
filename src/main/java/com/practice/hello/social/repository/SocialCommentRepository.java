package com.practice.hello.social.repository;


import com.practice.hello.social.entity.SocialBoard;
import com.practice.hello.social.entity.SocialComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocialCommentRepository extends JpaRepository<SocialComment,Long> {
    Optional<SocialComment> findById(Long id);


    Long countBySocialBoard(SocialBoard socialBoard); // countByFreeBoard로 변경

    @Query("SELECT c FROM SocialComment c WHERE c.socialBoard.id = :boardId")
    List<SocialComment> findByBoardId(@Param("boardId") Long boardId);



}
