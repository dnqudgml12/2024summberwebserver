package com.practice.hello.secretboard.repository;



import com.practice.hello.secretboard.entity.SecretBoard;
import com.practice.hello.secretboard.entity.SecretComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SecretCommentRepository extends JpaRepository<SecretComment,Long> {
    Optional<SecretComment> findById(Long id);


    Long countBySecretBoard(SecretBoard secretBoard); // countByFreeBoard로 변경

    @Query("SELECT c FROM SecretComment c WHERE c.secretBoard.id = :boardId")
    List<SecretComment> findByBoardId(@Param("boardId") Long boardId);



}
