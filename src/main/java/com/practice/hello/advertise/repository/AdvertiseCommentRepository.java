package com.practice.hello.advertise.repository;


import com.practice.hello.advertise.entity.AdvertiseBoard;
import com.practice.hello.advertise.entity.AdvertiseComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertiseCommentRepository extends JpaRepository<AdvertiseComment,Long> {
    Optional<AdvertiseComment> findById(Long id);


    Long countByAdvertiseBoard(AdvertiseBoard advertiseBoard); // countByFreeBoard로 변경

    @Query("SELECT c FROM AdvertiseComment c WHERE c.advertiseBoard.id = :boardId")
    List<AdvertiseComment> findByBoardId(@Param("boardId") Long boardId);



}
